package com.example.mbenben.chabaike.beans;

/**json:
 * "id":"8218",
 "title":"认识凤凰单丛茶",
 "source":"原创",
 "description":"",
 "wap_thumb":"http://s1.sns.maimaicha.com/images/2016/01/06/20160106110314_22333_suolue3.jpg",
 "create_time":"01月06日11:04",
 "nickname":"bubu123"
 * Created by MBENBEN on 2016/6/22.
 */
public class JsonInfo {
    private String js_id;
    private String js_title;
    private String source;
    private String description;
    private String wap_thumb;
    private String create_timme;

    public String getJs_id() {
        return js_id;
    }

    public void setJs_id(String js_id) {
        this.js_id = js_id;
    }

    public String getJs_title() {
        return js_title;
    }

    public void setJs_title(String js_title) {
        this.js_title = js_title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWap_thumb() {
        return wap_thumb;
    }

    public void setWap_thumb(String wap_thumb) {
        this.wap_thumb = wap_thumb;
    }

    public String getCreate_timme() {
        return create_timme;
    }

    public void setCreate_timme(String create_timme) {
        this.create_timme = create_timme;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String nickname;

  public JsonInfo(){

  }

    public JsonInfo(String js_id,String source,String description,String wap_thumb,
                    String create_timme,String js_title,String nickname){

        this.js_id =js_id;
        this.source = source;
        this.description = description;
        this.wap_thumb = wap_thumb;
        this.create_timme = create_timme;
        this.js_title = js_title;
        this.nickname = nickname;


    }

    @Override
    public String toString() {
        return "JsonInfo{" +
                "js_id='" + js_id + '\'' +
                ", js_title='" + js_title + '\'' +
                ", source='" + source + '\'' +
                ", description='" + description + '\'' +
                ", wap_thumb='" + wap_thumb + '\'' +
                ", create_timme='" + create_timme + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
