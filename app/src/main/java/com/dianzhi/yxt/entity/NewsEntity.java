package com.dianzhi.yxt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CK on 2017/2/17.
 */

public class NewsEntity {

    /**
     * link_v2_sync_img : http://jingxuan.guokr.com/pick/v2/78900/sync/
     * source_name : 闪电侠！
     * video_url :
     * current_user_has_collected : false
     * likings_count : 5
     * images : ["http://2.im.guokr.com/Y5f1mPPbVvtSEIJT_ImHe5G8cpBgkVvXIptX87d0AeBGAgAARwEAAEdJ.gif?imageView2/1/w/480/h/269","http://3.im.guokr.com/h7vgGX7EbpUoXuofI1_hQEKTLiwbYqwmfD1TA7zi8EJGAgAARwEAAEdJ.gif?imageView2/1/w/480/h/269","http://2.im.guokr.com/J2WyGeFt_Mk_Oze6YOoRXmmQfG3q5LxBrDe6cR7nY55_AgAApwEAAEpQ.jpg?imageView2/1/w/480/h/317","http://3.im.guokr.com/z1VmqBoxCAqWlcukh3Z4VsDeExjh8AcjtcceXDvTvVaCAgAAqQEAAEpQ.jpg?imageView2/1/w/480/h/317","http://3.im.guokr.com/J8yzfkdpZTv6pnE8qAWhGkKMz6e7JNN8nY_qT6hllNJ-AgAAqQEAAEpQ.jpg?imageView2/1/w/480/h/319","http://3.im.guokr.com/bbzHGTpcd2c1o6PwXR9F4T3VB8cGojqrY2uUJm-ew_eBAgAAqQEAAEpQ.jpg?imageView2/1/w/480/h/318","http://3.im.guokr.com/Y3RHF_RWK6Wq02xN94_PG-Hqml0jU4Mv5ITcElUJ5mKAAgAApwEAAEpQ.jpg?imageView2/1/w/480/h/317","http://3.im.guokr.com/HC72jRLlwscL1XeHao2W-6jmRZ1o3isZW3998h9YGZp9AgAApQEAAEpQ.jpg?imageView2/1/w/480/h/317"]
     * video_duration : null
     * id : 78900
     * category : science
     * style : article
     * title : 文艺科学：DNA结晶的梦幻世界
     * source_data : {"image":"http://2.im.guokr.com/r8PINb_RG_niPP_rsxxvHLK7HmQE9i1NZD6pWV_0VDKgAAAAoAAAAFBO.png?imageView2/1/w/160/h/160","summary":"今天你看了什么好玩儿的新闻！","id":52,"key":"闪电侠！","title":"闪电侠！"}
     * headline_img_tb : http://2.im.guokr.com/Y5f1mPPbVvtSEIJT_ImHe5G8cpBgkVvXIptX87d0AeBGAgAARwEAAEdJ.gif?imageView2/1/w/288/h/161
     * link_v2 : http://jingxuan.guokr.com/pick/v2/78900/
     * date_picked : 1487289660
     * is_top : false
     * link : http://jingxuan.guokr.com/pick/78900/
     * headline_img : http://2.im.guokr.com/Y5f1mPPbVvtSEIJT_ImHe5G8cpBgkVvXIptX87d0AeBGAgAARwEAAEdJ.gif
     * replies_count : 0
     * current_user_has_liked : false
     * page_source : http://jingxuan.guokr.com/pick/78900/?ad=1
     * author : 窗敲雨
     * summary : Linden Gledhill的本职工作是研发用于治疗癌症的药物，而在业余时间，他总是着迷于拍摄显微镜下的种种景象。而DNA的结晶
     * source : group
     * reply_root_id : 772491
     * date_created : 1487243416
     */
   public ArrayList<Item> result;

    public class Item{
        private String link_v2_sync_img;
        private String source_name;
        private String video_url;
        private boolean current_user_has_collected;
        private int likings_count;
        private String video_duration;
        private int id;
        private String category;
        private String style;
        private String title;
        private String headline_img_tb;
        private String link_v2;
        private int date_picked;
        private boolean is_top;
        private String link;
        private String headline_img;
        private int replies_count;
        private boolean current_user_has_liked;
        private String page_source;
        private String author;
        private String summary;
        private String source;
        private int reply_root_id;
        private int date_created;
        private List<String> images;

        public String getLink_v2_sync_img() {
            return link_v2_sync_img;
        }

        public void setLink_v2_sync_img(String link_v2_sync_img) {
            this.link_v2_sync_img = link_v2_sync_img;
        }

        public String getSource_name() {
            return source_name;
        }

        public void setSource_name(String source_name) {
            this.source_name = source_name;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public boolean isCurrent_user_has_collected() {
            return current_user_has_collected;
        }

        public void setCurrent_user_has_collected(boolean current_user_has_collected) {
            this.current_user_has_collected = current_user_has_collected;
        }

        public int getLikings_count() {
            return likings_count;
        }

        public void setLikings_count(int likings_count) {
            this.likings_count = likings_count;
        }

        public String getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(String video_duration) {
            this.video_duration = video_duration;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getHeadline_img_tb() {
            return headline_img_tb;
        }

        public void setHeadline_img_tb(String headline_img_tb) {
            this.headline_img_tb = headline_img_tb;
        }

        public String getLink_v2() {
            return link_v2;
        }

        public void setLink_v2(String link_v2) {
            this.link_v2 = link_v2;
        }

        public int getDate_picked() {
            return date_picked;
        }

        public void setDate_picked(int date_picked) {
            this.date_picked = date_picked;
        }

        public boolean isIs_top() {
            return is_top;
        }

        public void setIs_top(boolean is_top) {
            this.is_top = is_top;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getHeadline_img() {
            return headline_img;
        }

        public void setHeadline_img(String headline_img) {
            this.headline_img = headline_img;
        }

        public int getReplies_count() {
            return replies_count;
        }

        public void setReplies_count(int replies_count) {
            this.replies_count = replies_count;
        }

        public boolean isCurrent_user_has_liked() {
            return current_user_has_liked;
        }

        public void setCurrent_user_has_liked(boolean current_user_has_liked) {
            this.current_user_has_liked = current_user_has_liked;
        }

        public String getPage_source() {
            return page_source;
        }

        public void setPage_source(String page_source) {
            this.page_source = page_source;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getReply_root_id() {
            return reply_root_id;
        }

        public void setReply_root_id(int reply_root_id) {
            this.reply_root_id = reply_root_id;
        }

        public int getDate_created() {
            return date_created;
        }

        public void setDate_created(int date_created) {
            this.date_created = date_created;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

    }


}
