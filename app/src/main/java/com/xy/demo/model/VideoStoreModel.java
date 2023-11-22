package com.xy.demo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author: Leo
 * createDate: 2023/11/22 11:58
 */
public class VideoStoreModel {


    @SerializedName("banner")
    private List<BannerDTO> banner;
    @SerializedName("label")
    private List<LabelDTO> label;

    public List<BannerDTO> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDTO> banner) {
        this.banner = banner;
    }

    public List<LabelDTO> getLabel() {
        return label;
    }

    public void setLabel(List<LabelDTO> label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "{" +
                "\"banner\":" + banner +
                ", \"label\":" + label +
                '}';
    }

    public static class BannerDTO {
        @SerializedName("id")
        private Integer id;
        @SerializedName("title")
        private String title;
        @SerializedName("image")
        private String image;
        @SerializedName("status")
        private Integer status;
        @SerializedName("display_order")
        private Integer displayOrder;
        @SerializedName("lang_id")
        private Integer langId;
        @SerializedName("skip_type")
        private Integer skipType;
        @SerializedName("skip_content")
        private String skipContent;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getDisplayOrder() {
            return displayOrder;
        }

        public void setDisplayOrder(Integer displayOrder) {
            this.displayOrder = displayOrder;
        }

        public Integer getLangId() {
            return langId;
        }

        public void setLangId(Integer langId) {
            this.langId = langId;
        }

        public Integer getSkipType() {
            return skipType;
        }

        public void setSkipType(Integer skipType) {
            this.skipType = skipType;
        }

        public String getSkipContent() {
            return skipContent;
        }

        public void setSkipContent(String skipContent) {
            this.skipContent = skipContent;
        }
    }

    public static class LabelDTO {
        @SerializedName("recommend_id")
        private Integer recommendId;
        @SerializedName("label")
        private String label;
        @SerializedName("style")
        private Integer style;
        @SerializedName("can_more")
        private Boolean canMore;
        @SerializedName("can_refresh")
        private Boolean canRefresh;
        @SerializedName("list")
        private List<ListDTO> list;
        @SerializedName("expire_time")
        private Integer expireTime;

        public Integer getRecommendId() {
            return recommendId;
        }

        public void setRecommendId(Integer recommendId) {
            this.recommendId = recommendId;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getStyle() {
            return style;
        }

        public void setStyle(Integer style) {
            this.style = style;
        }

        public Boolean getCanMore() {
            return canMore;
        }

        public void setCanMore(Boolean canMore) {
            this.canMore = canMore;
        }

        public Boolean getCanRefresh() {
            return canRefresh;
        }

        public void setCanRefresh(Boolean canRefresh) {
            this.canRefresh = canRefresh;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public Integer getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Integer expireTime) {
            this.expireTime = expireTime;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"recommendId\":" + recommendId +
                    ", \"label\":\'" + label + "\'" +
                    ", \"style\":" + style +
                    ", \"canMore\":" + canMore +
                    ", \"canRefresh\":" + canRefresh +
                    ", \"list\":" + list +
                    ", \"expireTime\":" + expireTime +
                    '}';
        }

        public static class ListDTO {
            @SerializedName("video_id")
            private Integer videoId;
            @SerializedName("title")
            private String title;
            @SerializedName("source")
            private String source;
            @SerializedName("author")
            private String author;
            @SerializedName("description")
            private String description;
            @SerializedName("cover")
            private String cover;
            @SerializedName("status")
            private String status;
            @SerializedName("total_views")
            private Integer totalViews;
            @SerializedName("total_favors")
            private Integer totalFavors;
            @SerializedName("free_episode")
            private Integer freeEpisode;
            @SerializedName("episode_price")
            private Integer episodePrice;
            @SerializedName("likes_num")
            private String likesNum;
            @SerializedName("is_finished")
            private Boolean isFinished;
            @SerializedName("total_episode")
            private Integer totalEpisode;
            @SerializedName("keywords")
            private String keywords;
            @SerializedName("issue_date")
            private Integer issueDate;
            @SerializedName("episode_index")
            private Integer episodeIndex;
            @SerializedName("is_played")
            private Boolean isPlayed;

            public Integer getVideoId() {
                return videoId;
            }

            public void setVideoId(Integer videoId) {
                this.videoId = videoId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Integer getTotalViews() {
                return totalViews;
            }

            public void setTotalViews(Integer totalViews) {
                this.totalViews = totalViews;
            }

            public Integer getTotalFavors() {
                return totalFavors;
            }

            public void setTotalFavors(Integer totalFavors) {
                this.totalFavors = totalFavors;
            }

            public Integer getFreeEpisode() {
                return freeEpisode;
            }

            public void setFreeEpisode(Integer freeEpisode) {
                this.freeEpisode = freeEpisode;
            }

            public Integer getEpisodePrice() {
                return episodePrice;
            }

            public void setEpisodePrice(Integer episodePrice) {
                this.episodePrice = episodePrice;
            }

            public String getLikesNum() {
                return likesNum;
            }

            public void setLikesNum(String likesNum) {
                this.likesNum = likesNum;
            }

            public Boolean getIsFinished() {
                return isFinished;
            }

            public void setIsFinished(Boolean isFinished) {
                this.isFinished = isFinished;
            }

            public Integer getTotalEpisode() {
                return totalEpisode;
            }

            public void setTotalEpisode(Integer totalEpisode) {
                this.totalEpisode = totalEpisode;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public Integer getIssueDate() {
                return issueDate;
            }

            public void setIssueDate(Integer issueDate) {
                this.issueDate = issueDate;
            }

            public Integer getEpisodeIndex() {
                return episodeIndex;
            }

            public void setEpisodeIndex(Integer episodeIndex) {
                this.episodeIndex = episodeIndex;
            }

            public Boolean getIsPlayed() {
                return isPlayed;
            }

            public void setIsPlayed(Boolean isPlayed) {
                this.isPlayed = isPlayed;
            }
        }
    }
}
