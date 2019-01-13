package com.example.a79875.todaynews.Util;

import com.example.a79875.todaynews.enity.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 你是我的 on 2019/1/3.
 */

// 找不到视频接口，只能写数据
public class VideoUtil {

    List<Video> videoList = new ArrayList<>();
    List<String> videoUrlList = new ArrayList<>();
    List<String> videoFacePicList = new ArrayList<>();
    List<String> videoTitleList = new ArrayList<>();
    List<String> videoDescription = new ArrayList<>();
    List<String> videoAuthor = new ArrayList<>();

    public VideoUtil() {
        setVideoFacePicList();
        setVideoTitleList();
        setVideoUrlList();
        setVideoAuthor();
        setVideoDescription();
    }

    public List<Video> getVideoList() {
        videoList.clear();
        for (int i = 0; i < videoTitleList.size(); i++){
            Video video = new Video();
            video.setVideoTitle(videoTitleList.get(i));
            video.setVideoUrl(videoUrlList.get(i));
            video.setVideofacePic(videoFacePicList.get(i));
            video.setVideoAuthor(videoAuthor.get(i));
            video.setVideoDesctiption(videoDescription.get(i));
            videoList.add(video);
        }
        return videoList;
    }

    public void setVideoUrlList() {
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=144764&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=144763&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=144218&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=87287&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=142206&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=141241&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=144257&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=137820&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=135588&resourceType=video&editionType=default&source=aliyun");
        videoUrlList.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=140671&resourceType=video&editionType=default&source=aliyun");
    }

    public void setVideoFacePicList() {
        videoFacePicList.add("http://img.kaiyanapp.com/bc2479c09cd15cb93b69d82e5f21c3fc.png?imageMogr2/quality/60/format/jpg");
        videoFacePicList.add("http://img.kaiyanapp.com/de35dbc62365e90cca7560b9e3d51360.jpeg?imageMogr2/quality/60/format/jpg");
        videoFacePicList.add("http://img.kaiyanapp.com/f2e7359e81e217782f32cc3d482b3284.jpeg?imageMogr2/quality/60/format/jpg");
        videoFacePicList.add("http://img.kaiyanapp.com/9056413cfeffaf0c841d894390aa8e08.jpeg?imageMogr2/quality/60/format/jpg");
        videoFacePicList.add("http://img.kaiyanapp.com/2c869d91d5db6ecf1c8c46af0f448719.png?imageMogr2/quality/60/format/jpg");
        videoFacePicList.add("http://img.kaiyanapp.com/072e8eb0c130ed248b26c91d194cb10d.jpeg?imageMogr2/quality/100");
        videoFacePicList.add("http://img.kaiyanapp.com/a082f44b88e78daaf19fa4e1a2faaa5a.jpeg?imageMogr2/quality/60/format/jpg");
        videoFacePicList.add("http://img.kaiyanapp.com/c4e5c0f76d21abbd23c9626af3c9f481.jpeg?imageMogr2/quality/100");
        videoFacePicList.add("http://img.kaiyanapp.com/d9163ebc9c8ffcdccee2d7855b441d17.png?imageMogr2/quality/60/format/jpg");
        videoFacePicList.add("http://img.kaiyanapp.com/f2e7359e81e217782f32cc3d482b3284.jpeg?imageMogr2/quality/60/format/jpg");
    }

    public void setVideoTitleList() {
        videoTitleList.add("创意摄影：再见 2018，你好 2019");
        videoTitleList.add("百发百中！一整年的强迫症都被治愈了");
        videoTitleList.add("中国的设计徘徊不前，是因为我们脑子里有墙");
        videoTitleList.add("反思广告：充满仇恨的 2018");
        videoTitleList.add("颜值新高！炙烤寿司甜甜圈来一个");
        videoTitleList.add("人生要始终坚信，你一定会度过一切难关！");
        videoTitleList.add("牛掰！74 岁景区奶奶飙 11 门外语卖水");
        videoTitleList.add("奥斯卡提名动画：「CAGE-折枝」");
        videoTitleList.add("超刺激追击跑酷！你能想到的动作片桥段都有");
        videoTitleList.add("职场必修课：如何从工作中找到满足感？");
    }

    public void setVideoDescription(){
        videoDescription.add("短片作者用韩国各地的标志牌巧妙串联出对过去一年的总结，以及对未来一年的期许。不管 2018 你经历过什么，新的旅程已经启航，勇敢向前冲吧！From Bluck");
        videoDescription.add( "运动喜剧届最会玩的 Dude Perfect 如约献上了 2018 精彩集锦。别看他们四肢发达、鬼点子一点也不少。展示高超技巧的同时，还增加了很多趣味和创意，让你立刻爱上运动的感觉。BGM：Zayde Wølf 「We Got the Power」。");
        videoDescription.add("柳冠中，清华大学美术学院教授。从中国制造到中国创造，设计应当扮演何种角色？为什么一直强调应该把设计提升到国家战略？为什么说我们没有真正的市场观念，都是商场观念？");
        videoDescription.add("每一次摔倒，每一次感觉你自己无法再站立。此时的你一定要坚信，一切都会过去，一切都会好的，你能够度过这一个难关，成长为更勇敢的你自己！From Ben Lionel Scott");
        videoDescription.add( "「小学没毕业自学 11 国语言，74 岁奶奶征服网友」徐秀珍在景区卖水补贴家用，只有小学四年级文化的她，自学了 11 门外语，还能用英语和游客熟练进行交流，被网友称为「月亮妈妈」。她说「存多点钱，不要成为儿子的负担，千有万有不如自己有。」");
        videoDescription.add( "一部具有庄周梦蝶东方美学的动画短片，一部关于东方女性命运轮回的前卫之作，一位来自南京毕业于纽约普瑞特艺术学院动画专业的中国留学生，以一人之力核心创作完成了这部名为「折枝」的二维动画短片。脑洞大开层层嵌套的梦中梦，辨不清现实与梦境，游离于真实与虚幻。极具东方特色的美丽女子如同虚空幻影，身躯舞动飘逸灵动至极，却始终难以摆脱背负沉重枷锁的宿命。她是观画之人，亦是笼中之鸟；她是画中端盘之人，亦是盘中之餐。");
        videoDescription.add("跑酷、滑板、追击、枪战，这些不同的元素集合到一个视频上，真的让人大呼过瘾！带上隐形眼镜进入现实版枪战游戏的设定，在集装箱之间你追我赶，还有比这更刺激的跑酷吗！From STORROR");
        videoDescription.add("找到一份满意的工作的关键是多思考，分析自己的恐惧，了解市场，学会反思，不要被主流的标准误导。或许你有时候会对职场产生恐惧并充满困惑，所以从工作中找寻满足感是对你来说极为重要的事情。");
        videoDescription.add("短片作者用韩国各地的标志牌巧妙串联出对过去一年的总结，以及对未来一年的期许。不管 2018 你经历过什么，新的旅程已经启航，勇敢向前冲吧！From Bluck");
        videoDescription.add("短片作者用韩国各地的标志牌巧妙串联出对过去一年的总结，以及对未来一年的期许。不管 2018 你经历过什么，新的旅程已经启航，勇敢向前冲吧！From Bluck");
    }

    public void setVideoAuthor(){
        videoAuthor.add("创意灵感");
        videoAuthor.add("城会玩");
        videoAuthor.add("科普");
        videoAuthor.add("生活方式");
        videoAuthor.add("记录精选");
        videoAuthor.add("动画");
        videoAuthor.add("运动");
        videoAuthor.add("科普");
        videoAuthor.add("创意灵感");
        videoAuthor.add("创意灵感");
    }
}
