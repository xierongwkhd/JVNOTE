package moke.demo.ssm.controller;

import moke.demo.ssm.common.PageHelper;
import moke.demo.ssm.entity.User;
import moke.demo.ssm.entity.UserContent;
import moke.demo.ssm.service.CommentService;
import moke.demo.ssm.service.SolrService;
import moke.demo.ssm.service.UpvoteService;
import moke.demo.ssm.service.UserContentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by MOKE on 2019/2/18.
 */
@Controller
public class WriteController extends BaseController {
    private final static Logger log = Logger.getLogger(WriteController.class);
    @Autowired
    private UserContentService userContentService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UpvoteService upvoteService;
    @Autowired
    private SolrService solrService;

    @RequestMapping("/deleteContent")
    public String deleteContent(Model model, @RequestParam(value = "cid",required = false) Long cid) {

        User user = getCurrentUser();
        if(user==null) {
            return "../login";
        }
        commentService.deleteByContentId(cid);
        upvoteService.deleteByContentId(cid);
        userContentService.deleteById(cid);
        solrService.deleteById(cid);
        return "redirect:/list?manage=manage";
    }


    //个人主页跳转至书写页面
    @RequestMapping("/writedream")
    public String writedream(Model model,@RequestParam(value = "cid",required = false) Long cid) {
        User user = getCurrentUser();
        if(cid!=null){
            UserContent content = userContentService.findById(cid);
            model.addAttribute("cont",content);
        }
        model.addAttribute("user", user);
        return "write/writedream";
    }

    //添加文章
    @RequestMapping("/doWritedream")
    public String doWritedream(Model model, @RequestParam(value = "id",required = false) String id,
                               @RequestParam(value = "cid",required = false) Long cid,
                               @RequestParam(value = "category",required = false) String category,
                               @RequestParam(value = "txtT_itle",required = false) String txtT_itle,
                               @RequestParam(value = "content",required = false) String content,
                               @RequestParam(value = "private_dream",required = false) String private_dream) {
        log.info( "进入写博客Controller" );
        User user = getCurrentUser();
        if(user == null){
            //未登录
            model.addAttribute( "error","请先登录！" );
            return "../login";
        }
        UserContent userContent = new UserContent();
        if(cid!=null){
            userContent = userContentService.findById(cid);
        }
        userContent.setCategory( category );
        userContent.setContent( content );
        userContent.setRptTime( new Date(  ) );
        String imgUrl = user.getImgUrl();
        if(StringUtils.isBlank( imgUrl )){
            userContent.setImgUrl( "/images/icon_m.jpg" );
        }else {
            userContent.setImgUrl( imgUrl );
        }
        if("on".equals( private_dream )){
            userContent.setPersonal( "1" );
        }else{
            userContent.setPersonal( "0" );
        }
        userContent.setTitle( txtT_itle );
        userContent.setuId( user.getId() );
        userContent.setNickName( user.getNickName() );

        if(cid ==null){
            userContent.setUpvote( 0 );
            userContent.setDownvote( 0 );
            userContent.setCommentNum( 0 );
            userContentService.addContent( userContent );
            solrService.addUserContent(userContent);

        }else {
            userContentService.updateById(userContent);
            solrService.updateUserContent(userContent);
        }
        model.addAttribute("content",userContent);
        return "write/writesuccess";
    }

    /**
     * 根据文章id查看文章
     */
    @RequestMapping("/watch")
    public String watchContent(Model model, @RequestParam(value = "cid",required = false) Long cid){
        User user = getCurrentUser();
        if(user == null){
            //未登录
            model.addAttribute( "error","请先登录！" );
            return "../login";
        }
        UserContent userContent = userContentService.findById(cid);
        model.addAttribute("cont",userContent);
        return "personal/watch";
    }

    /**
     * 根据用户id查看文章
     */
    @RequestMapping("/watchc")
    public String watchContent2(Model model,
                                @RequestParam(value = "pageNum",required = false) Integer pageNum ,
                                @RequestParam(value = "pageSize",required = false) Integer pageSize){
        User user = getCurrentUser();
        if(user == null){
            //未登录
            model.addAttribute( "error","请先登录！" );
            return "../login";
        }

        PageHelper.Page<UserContent> page = userContentService.findByUserId(user.getId(),pageNum,pageSize);
        model.addAttribute("page",page);
        return "personal/watch";
    }

    //依据热度
    @RequestMapping("/watchh")
    public String watchContent3(Model model,
                                @RequestParam(value = "pageNum",required = false) Integer pageNum ,
                                @RequestParam(value = "pageSize",required = false) Integer pageSize){
        UserContent uct = new UserContent();
        uct.setPersonal("0");
        PageHelper.Page<UserContent> hotPage =  findAllByUpvote(uct,pageNum, pageSize);
        model.addAttribute("page",hotPage);
        return "personal/hotblog";
    }
}
