--自增序列
Create sequence t_user_id_seq Minvalue 1001 Nomaxvalue Increment by 1 start with 1001 nocache;
Create sequence t_game_id_seq Minvalue 1001 Nomaxvalue Increment by 1 start with 1001 nocache;
Create sequence t_tag_id_seq Minvalue 1001 Nomaxvalue Increment by 1 start with 1001 nocache;
Create sequence t_ugr_id_seq Minvalue 1001 Nomaxvalue Increment by 1 start with 1001 nocache;
Create sequence t_gtr_id_seq Minvalue 1001 Nomaxvalue Increment by 1 start with 1001 nocache;

--建表语句
Create table tbl_users(
  USERID int,
  TYPE int NOT NULL,
  NAME varchar2(256) NOT NULL,
  PASSWORD varchar2(16) NOT NULL,
  AVATAR varchar2(256) NOT NULL,
  SURPLUS int NOT NULL,
  COOKIE varchar2(256) NOT NULL,
  constraint PK_USER_FS PRIMARY KEY (USERID)
);

Create table tbl_games(
  GID int,
  NAME varchar2(256) NOT NULL,
  DEV varchar2(256) NOT NULL,
  PUB varchar2(256) NOT NULL,
  PRICE int NOT NULL,
  DISCOUNT int NOT NULL,
  SUMMARY varchar2(512) NOT NULL,
  PUBLISH_DATE varchar2(16) NOT NULL,
  MAIN_IMAGE varchar2(256) NOT NULL,
  STATUS int NOT NULL,
  constraint PK_GAME_FS PRIMARY KEY (GID)
);

Create table tbl_tags(
  TID int,
  NAME varchar2(16) NOT NULL,
  constraint PK_TAG_FS PRIMARY KEY (TID)
);

Create table tbl_user_game_relation(
  UGR int,
  USERID int,
  GID int,
  STATUS int,
  constraint PK_UGR_FS PRIMARY KEY (UGR),
  constraint FK_UGR_USER_FS FOREIGN KEY (USERID) REFERENCES tbl_users(USERID),
  constraint FK_UGR_GAME_FS FOREIGN KEY (GID) REFERENCES tbl_games(GID)
);

Create table tbl_game_tag_relation(
  GTR int,
  GID int,
  TID int,
  constraint PK_GTR_FS PRIMARY KEY (GTR),
  constraint FK_GTR_GAME_FS FOREIGN KEY (GID) REFERENCES tbl_games(GID),
  constraint FK_GTR_TAG_FS FOREIGN KEY (TID) REFERENCES tbl_tags(TID)
);

--用户表初始化插入语句
INSERT INTO tbl_users VALUES(t_user_id_seq.nextval,1,'Kirin','1234566','',1200,'aaa');
INSERT INTO tbl_users VALUES();


--游戏表初始化插入语句
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'游魂2-you are the only one-','Lump of Sugar','HIKARI FIELD',11900,71,
'本作是由知名美少女游戏品牌Lump of Sugar（方糖社）制作的一款奇幻恋爱题材作品。也是高人气作品《游魂-Kiss on my Deity-》时隔8年
推出正统续作。本作仍由前作原画师萌木原文武担当原画人设，剧情则在继承原有世界观基础上引入新角色与故事，同时又妥善照顾了玩家的情怀面
让前作主角们悉数登场。','2016-12-9','tayutama2',1);
INSERT INTO tbl_games VALUES(t_tag_id_seq.nextval,'CLANNAD','VisualArts/Key','Sekai Project',12600,50,'空前热门的作品「CLANNAD」
通过「家族」这一主题，以宏大的剧本、美丽的CG和动人的音乐，描绘了一个人与小镇、人与人的故事。','2004-4-28','clannad',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'DATE A LIVE: Rio Reincarnation','Idea Factory','Idea Factory International',13800,60,
'Show the Spirits the power of love and save the world in this romantic visual novel!','2019-7-24','DAL-rio',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'How to Raise a Wolf Girl','Sweet & Tea','Sekai Project',7000,26,
'Shuuji and Kana have their hands full teaching Iroha how to be human again, after she spends ten years in the
mountains with a wolf god. But who is really raising who?','2019-10-12','How-to-Raise-a-Wolf-Girl',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'If My Heart Had Wings','MoeNovel','MoeNovel',4800,75,'本作品支持简体中文，请先购买本体
再进行DLC免费更新为中文版。主角水瀬碧梦想成为一名自行车选手，但在一次自行车事故中不能再骑车，因此沮丧的水瀬碧回到家乡重新开始生活。失去了动力
的水瀬碧偶然遇上被困在山坡上女主角小鸟。并一同亲眼目睹一架滑翔机在高空飞行时，开始了飞行梦想的旅程。','2013年6月28日','If-My-Heart-Had-Wings',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'The logic of the miniature garden','Cabbit','SakuraGame',3600,50,'本作
是一款有著萌系畫風，與美少女一起探索神秘的學園推理ADV作品。作為懸疑向作品，在推理解謎之外，著重強調對角色的刻畫，在充滿黑暗的“箱庭市”裡，
作為普通人的男主無法改變現狀，遊戲從一個男主見證者的視角，給玩家傳達了城市裡人性的“惡”。','2019-6-19','The-logic-of-the-miniature-garden',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'Muv-Luv','aNCHOR Inc.','aNCHOR Inc.',9000,40,
'白陵大附属柊学园3年级白银武和青梅竹马鉴纯夏，好友铠衣尊人过着愉快普通的校园生活。某一天的早上，醒来的武发现身旁睡着一名少女。
这位名叫御剑冥夜的少女就这样住了下来。','2016-7-15','Muv-Luv',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'Muv-Luv Alternative','aNCHOR Inc.','aNCHOR Inc.',11600,41,
'──那是沒被說出的另類結束。非常渺小的、非常壯大的、非常重要的、愛與勇氣的童話故事──','2017年9月18日','Muv-Luv-Alternative',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'NEKO-NIN exHeart','Whirlpool','Sekai Project',3600,61,'Japan. From time immemorial,
there are those who possess superhuman abilities. These beings who looked like a cross between man and beast are called Demi-Humans.
They acted as Ninja and served those in power.','2017年4月22日','NEKO-NIN-exHeart',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'NEKO-NIN exHeart 2','Whirlpool','Sekai Project',3700,51,'This is a Sequel to
NEKO-NIN exHeart. Japan. From time immemorial, there are those who possess superhuman abilities. These beings who looked like a
cross between man and beast are called Demi-Humans. They acted as Ninja and served those in power.','2018-4-28','NEKO-NIN-exHeart2',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'NEKO-NIN exHeart 3','Whirlpool','Sekai Project',3700,32,
'We have arrived from the Fuuma Village in order to serve you, sir!','2019-10-19','NEKO-NIN-exHeart3',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'NEKOPARA Vol.0','NEKO WORKs','Sekai Project',1500,60,
'Please note that NEKOPARA Vol. 0 is a fandisc for NEKOPARA Vol. 1What is NEKOPARA? Why, it is a cat paradise!
This is a story just before Kashou opened "La Soleil".Enjoy a page from the leisurely, daily lives of Shigure and
the Minaduki household''s catgirls!','2015-8-17','nekopara0',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'NEKOPARA Vol.1','NEKO WORKs','Sekai Project',3600,61,
'水無月嘉祥離開了經營著具有悠久傳統的和菓子老店的老家， 決心以西點師的身分，獨力開一間自己的蛋糕店"La Soleil"。 但是從老家寄來的
搬家行李裡頭， 竟然混進了兩隻以前在老家養的人型貓，巧克力與香草。','2014-12-30','nekopara1',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'NEKOPARA Vol.2','NEKO WORKs','Sekai Project',3600,61,
'由水無月嘉祥經營的蛋糕店「Soleil」，今天也在水無月家貓娘四姊妹＋妹妹時雨的幫忙下努力營業著。 嘴巴壞又不坦率，但實際上十項全能又愛護妹妹的長女紅豆，
以及正直又努力，可是卻笨手笨腳又愛逞強的四女椰子， 過去在姊妹中感情最好的這兩隻貓娘，不知何時開始變成了一對成天吵不膩的歡喜冤家。','2016-2-20','nekopara2',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'NEKOPARA Vol.3','NEKO WORKs','Sekai Project',3600,50,
'水無月嘉祥所經營的蛋糕店『Soleil』裡的貓娘情人持續增加，今天也好評營業中。 自尊心很強、高傲又時髦的貓娘次女楓， 與總是陷入妄想獨自一人暴走的三女桂。
 兩隻貓在姊妹之中的關係如同至交好友一般，因為一些契機而對夢想感到煩惱的楓， 想成為摯友的助力卻不知該怎麼做才好的桂，描繪一起朝著夢想成','2017-5-25','nekopara3',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'STEINS;GATE','MAGES. Inc.','Spike Chunsoft Co., Ltd.',9000,60,
'由5pb.和nitroplus共同开发的《STEINS;GATE》曾被Famitsu杂志授予年度优秀游戏奖项，并在Famitsu读者调查中被评为最煽情的“催泪游戏”。
如此扣人心弦的视觉小说游戏，现已被改编为漫画、动画及剧场版等。','2016-9-9','STEINS;GATE',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'三色绘恋','HL-Galgame','SakuraGame',1100,64,'时光一逝永不回，往事只能回味。
100万字剧本+全程语音+100张CG的这部高中题材恋爱游戏仿佛带你回到学生时代，再经历一次高中恋爱生活。那一天，你失去了她；
那一天，她和她同时向你伸出了双手，你不再孤身一人。','2017-9-21','Tricolour-Lovestory',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'千恋万花','Yuzusoft','HIKARI FIELD',8800,16,'《千恋万花》是日本美少女游戏品牌Yuzusoft（柚子社）
2016年制作的一款和风恋爱题材作品。本作发售后展现出了势如破竹的高人气，并获得萌系游戏大赏的年度准大奖以及Getchu美少女游戏大赏中获得综合类
排名第一的殊荣，在剧本、系统、图像、音乐、影片、角色等多个奖项中也均有斩获。','2020-2-14','Tricolour-Lovestory',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'喵可莉的兔玩偶','赤瞳大白猫','赤瞳大白猫',1500,27,'在一个美妙的夜晚下醒来，
猫耳少女喵露朵露薇发现自己的伙伴不见了踪影，等待着她的究竟是什么呢？','2020-2-3','Nyakori',1);
INSERT INTO tbl_games VALUES(t_game_id_seq.nextval,'苍之彼方的四重奏','sprite','NekoNyan Ltd',8800,20,'《苍之彼方的四重奏》是日本知名美少女游戏品牌
sprite发售的超人气作品。这款以飞行运动为主题的作品在发售前夕就已集结大量人气，在发售后更是广受玩家赞誉成为经典。本作在2014年萌系游戏大赏中
获得年度排名第一名、玩家支持赏金奖以及年度大赏。是当之无愧的年度大赢家。','2019-9-28','Aokana',1);

--Tag表初始化插入语句
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'动漫');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'可爱');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'视觉小说');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'恋爱模拟');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'色情内容');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'剧情丰富');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'爱情');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'剧情');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'单人');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'模拟');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'裸露');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'成人');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'冒险');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'独立');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'休闲');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'暴力');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'血腥');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'Hentai');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'时空旅行');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'科幻');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'角色扮演');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'恐怖');
INSERT INTO tbl_tags VALUES(t_tag_id_seq.nextval,'RPG制作大师');

--用户库存表初始化插入语句
INSERT INTO tbl_user_game_relation VALUES();
INSERT INTO tbl_user_game_relation VALUES();

--游戏Tag表初始化插入语句
INSERT INTO tbl_game_tag_relation VALUES(
  t_gtr_id_seq.nextval,
  SELECT GID FROM tbl_games WHERE name = '三色绘恋',
  SELECT TID FROM tbl_tags WHERE name = '动漫'
);
commit;
INSERT INTO tbl_game_tag_relation VALUES();


