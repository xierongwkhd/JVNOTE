---
title: Java 实现附近视频功能
date: 2019-08-08 18:01:28
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/98619517](https://blog.csdn.net/MOKEXFDGH/article/details/98619517)   
    
  在实习的项目中，获取视频列表时，需要实现一个按照附近视频获取列表的接口，通过爬文，一般有如下三种解决方法：

  
  * 上传视频时，保存地点的经、纬度到数据库，根据用户位置计算边界值，数据库进行范围索引查询。 
  * 通过 geohash 算法将经纬度转换成一个字符串，保存到数据库，用户位置也转换成字符串，然后进行模糊查询。 
  * 使用能保存地理位置信息的数据库，如：mongodb、sqlserver、mysql5.7+。  
--------
 
### []()保存经纬度

 由于项目使用的 mysql5.5 数据库，所以采用的第一种方法，主要是需要确定查询的范围，即经度的范围和纬度的范围。  
 而一般我们所说的附近是一个圆形区域，我们先查询其最小的矩形区域内的视频，然后再计算这些视频的距离，剔除掉范围外的数据。

 **1.根据用户经纬度和范围，计算出矩形最大最小经纬度**

 
```
		private static double EARTH_RADIUS = 6378.137;// 地球半径

	    private static double rad(double d) {
	        return d * Math.PI / 180.0;//角度和弧度互换：1度=π/180
	    }
	
	    public static double[] getAround(double lat, double lon, int raidus) {//经度、纬度、范围
	
	        Double latitude = lat;
	        Double longitude = lon;
	
	        Double degree = (24901 * 1609) / 360.0;//2*PI*6378137 = 24901 * 1609，计算每一度有多长
	        double raidusMile = raidus;
	
	        Double dpmLat = 1 / degree;
	        Double radiusLat = dpmLat * raidusMile;//将radius转为度数
	        Double minLat = latitude - radiusLat;
	        Double maxLat = latitude + radiusLat;
	
	        Double mpdLng = degree * Math.cos(rad(latitude));
	        Double dpmLng = 1 / mpdLng;
	        Double radiusLng = dpmLng * raidusMile;
	        Double minLng = longitude - radiusLng;
	        Double maxLng = longitude + radiusLng;
	        return new double[] {minLat, minLng, maxLat, maxLng};
	    }

```
 **2.根据两点的经纬度计算距离：**

 
```
		public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
	        double radLat1 = rad(lat1);
	        double radLat2 = rad(lat2);
	        double a = radLat1 - radLat2;
	        double b = rad(lng1) - rad(lng2);
	
	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
	                                           + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
	        s = s * EARTH_RADIUS;
	        s = s * 1000;
	        s = Math.round(s * 10000) / 10000;
	        s = Math.ceil(s / 100) / 10;
	        return s;
	    }

```
 **3.数据库查询**  
 可以通过example类简化我们的代码：

 
```
		public List<Video> getByNearBy(BigDecimal longitude, BigDecimal latitude) {
			VideoExample example= new VideoExample();
			Criteria cri = example.createCriteria();
			double[] raidus = GetLatAndLngUtil.getAround(latitude.doubleValue(), longitude.doubleValue(),configInfo.getRaidus());
			//加入范围条件
			cri.andLatitudeBetween(new BigDecimal(raidus[0]), new BigDecimal(raidus[2]));
			cri.andLongitudeBetween(new BigDecimal(raidus[1]), new BigDecimal(raidus[3]));
			List<Video> list = videoMapper.SelectByExample(criteria);//获取矩形范围内的记录
			List<Video> res = new ArrayList<Video>();
			for (Video video: list) {//筛选出在圆形范围内的记录
				double distance = GetLatAndLngUtil.GetDistance(latitude.doubleValue(), longitude.doubleValue(),
						moment.getLatitude().doubleValue(), moment.getLongitude().doubleValue());
				res.add(video);
			}
			return res;
		}

```
 
--------
 
### []()Geohash 算法

 [https://blog.csdn.net/universe_ant/article/details/74785989](https://blog.csdn.net/universe_ant/article/details/74785989)

 
--------
 
### []()MySQL 空间数据

 MySQL 在 5.7 之后的版本支持了空间索引，也就是说我们可以索引来查找包含符合条件的空间数据的记录。

 **空间数据：**

  
  * 点 POINT(15 20) 
  * 线 LINESTRING(0 0, 10 10, 20 25, 50 60) 
  * 面 POLYGON((0 0,10 0,10 10,0 10,0 0),(5 5,7 5,7 7,5 7, 5 5)) 
  * 多个点 MULTIPOINT(0 0, 20 20, 60 60) 
  * 多个线 MULTILINESTRING((10 10, 20 20), (15 15, 30 15)) 
  * 多个面 MULTIPOLYGON(((0 0,10 0,10 10,0 10,0 0)),((5 5,7 5,7 7,5 7, 5 5))) 
  * 集合 GEOMETRYCOLLECTION(POINT(10 10), POINT(30 30), LINESTRING(15 15, 20 20))，简称GEOMETRY，可以放入点、线、面。  **示例：**  
 1.创建字段 location 类型为 POINT，并创建空间索引：

 
```
		CREATE TABLE `demo` (
		  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
		  `name` varchar(20) NOT NULL DEFAULT '',
		  `location` point NOT NULL,
		  PRIMARY KEY (`id`),
		  SPATIAL KEY `sp_index` (`location`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```
 2.插入记录：

 
```
	INSERT INTO points VALUES (1,'testlocation',POINT(116.397389,39.908149));

```
 3.查询记录：

 
```
	# 定义范围，即定义一个面
	SET @rect = CONCAT('POLYGON((116.373871 39.915786,116.417645 39.916444,116.41816 39.900841,116.374214 39.900182,116.373871 39.915786))');
	# 用定义的面进行范围查询
	select name,X(location),Y(location),Astext(location) from demo where INTERSECTS( location, GEOMFROMTEXT(@rect) ) ;

```
   
  