package com.smart.comm.utils;

public interface BBOOConstants {
	/**
	 * -----------常量命名规则：（表_表字段_字段内容）------------------
	 */
	
	/**
	 * 1支付成功，等待出票
	 */
	public final static String GjSaleOrderEntity_SLFSTATUS_ONE="1";
	/**
	 * 2出票完成
	 */
	public final static String GjSaleOrderEntity_SLFSTATUS_TWO="2";
	/**
	 * 5出票中
	 */
	public final static String GjSaleOrderEntity_SLFSTATUS_FIVE="5";
	/**
	 * 1支付成功，等待出票
	 */
	public final static String GjSaleOrderEntity_STATUS_ONE="1";
	/**
	 * 2出票完成
	 */
	public final static String GjSaleOrderEntity_STATUS_TWO="2";
	/**
	 * 5出票中
	 */
	public final static String GjSaleOrderEntity_STATUS_FIVE="5";
	/**
	 * 国际订单
	 */
	public final static String GjSaleOrderEntity_flightClass_I="I";
	/**
	 * 国内订单
	 */
	public final static String GjSaleOrderEntity_flightClass_N="N";
	//航程类型 S：单程 D：往返 T其它 L 联程 X 多程
	public final static String GjSaleOrderEntity_flightType_S="S";
	//航程类型 S：单程 D：往返 T其它 L 联程 X 多程
	public final static String GjSaleOrderEntity_flightType_D="D";
	//航程类型 S：单程 D：往返 T其它 L 联程 X 多程
	public final static String GjSaleOrderEntity_flightType_T="T";
	//航程类型 S：单程 D：往返 T其它 L 联程 X 多程
	public final static String GjSaleOrderEntity_flightType_L="L";
	//航程类型 S：单程 D：往返 T其它 L 联程 X 多程
	public final static String GjSaleOrderEntity_flightType_X="X";
	/**
	 * 分销商    T:淘宝 Q 去哪儿 C 携程 H 航班管家 K 酷讯 N 途牛 O 同程
	 */
	public final static String GjSaleOrderEntity_distributor_T="T";
	/**
	 * 分销商    T:淘宝 Q 去哪儿 C 携程 H 航班管家 K 酷讯 N 途牛 O 同程
	 */
	public final static String GjSaleOrderEntity_distributor_Q="Q";
	/**
	 * 分销商    T:淘宝 Q 去哪儿 C 携程 H 航班管家 K 酷讯 N 途牛 O 同程
	 */
	public final static String GjSaleOrderEntity_distributor_C="C";
	/**
	 * 分销商    T:淘宝 Q 去哪儿 C 携程 H 航班管家 K 酷讯 N 途牛 O 同程
	 */
	public final static String GjSaleOrderEntity_distributor_H="H";
	/**
	 * 分销商    T:淘宝 Q 去哪儿 C 携程 H 航班管家 K 酷讯 N 途牛 O 同程
	 */
	public final static String GjSaleOrderEntity_distributor_K="K";
	/**
	 * 分销商    T:淘宝 Q 去哪儿 C 携程 H 航班管家 K 酷讯 N 途牛 O 同程
	 */
	public final static String GjSaleOrderEntity_distributor_N="N";
	/**
	 * 分销商    T:淘宝 Q 去哪儿 C 携程 H 航班管家 K 酷讯 N 途牛 O 同程
	 */
	public final static String GjSaleOrderEntity_distributor_O="O";
	// 1 自动出票 2初始状态
	public final static Integer GjSaleOrderEntity_isAuto_one=1;
	// 1 自动出票 2初始状态
	public final static Integer GjSaleOrderEntity_isAuto_two=2;
	//  1政策已经缓存 2初始状态
	public final static Integer GjSaleOrderEntity_isGet_one=1;
	//  1政策已经缓存 2初始状态
	public final static Integer GjSaleOrderEntity_isGet_two=2;
	//  1是新预定的编码
	public final static Integer GjSaleOrderEntity_isNewCode_one=1;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_one=1;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_two=2;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_three=3;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_four=4;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_five=5;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_six=6;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_seven=7;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_eight=8;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_nine=9;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_ten=10;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_eleven=11;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_twelve=12;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_thirteen=13;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_fourteen=14;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_fifteen=15;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_sixteen=16;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_seventeen=17;
	/**
	 * 
	 * 政策类型 1 NFD 2 清仓产品 3 商旅产品 5 中转产品 6 包机 7 切位 8 航司VIP卡 10000 普通 10001 规则运价 10
	 * 金牌 11 远期 13竞价 14特价 15特惠 16 让利 17 特殊 30 优选 31 立减 -1其它
	 * 
	 * 10001 规则运价 10002 公布运价 10003 私有运价 10004 供应商直连 10005 申请 10006 包机/切位 10007
	 * 直销 10008 特惠 10009 固定 K 位 10010 清仓产品 10011 公布转私有 10021 包机/切位 10022 申
	 */
	public final static Integer GjSaleOrderEntity_policyType_thirty=30;
	/**
	 * a very large time
	 */
	public final static String GjSaleOrderEntity_pnrNoTime_max="9999-12-31 00:00";
	/**
	 * 回填客票状态 3失败 1成功 2默認
	 */
	public final static Integer GjSaleOrderEntity_backno_one=1;
	/**
	 * 回填客票状态 3失败 1成功 2默認
	 */
	public final static Integer GjSaleOrderEntity_backno_two=2;
	/**
	 * 回填客票状态 3失败 1成功 2默認
	 */
	public final static Integer GjSaleOrderEntity_backno_three=3;
	
	
	/**
	 * Swith表中的mkey值
	 */
//	public final static String SWITCH_MKEY_QUNAR_I="QUNAR_I_SYNC_ORDER_ON";
//	public final static String SWITCH_MKEY_CTRIP_I="CTRIP_I_SYNC_ORDER_ON";
//	public final static String SWITCH_MKEY_TAOBAO_I="TAOBAO_I_SYNC_ORDER_ON";
//	public final static String SWITCH_MKEY_QUNAR_N="QUNAR_N_SYNC_ORDER_ON";
//	public final static String SWITCH_MKEY_CTRIP_N="CTRIP_N_SYNC_ORDER_ON";
//	public final static String SWITCH_MKEY_TAOBAO_N="TAOBAO_N_SYNC_ORDER_ON";
//	public final static String SWITCH_MKEY_QUNAR="QUNAR_XUNJIA_MASTER_ON";
//	public final static String SWITCH_MKEY_CTRIP="CTRIP_XUNJIA_MASTER_ON";
//	public final static String SWITCH_MKEY_TAOBAO="TAOBAO_XUNJIA_MASTER_ON";
	/**
	 * 国际订单派单开关
	 */
	public final static String SWITCH_MKEY_dispatchOrderI="DISPATCH_ORDER_I";
	/**
	 * 国内订单派单开关
	 */
	public final static String SWITCH_MKEY_dispatchOrderN="DISPATCH_ORDER_N";
	/**
	 * 开关，0表示关
	 */
	public final static Integer SWITCH_ONOFF_OFF=0;
	/**
	 * 开关，1表示开
	 */
	public final static Integer SWITCH_ONOFF_ON=1;
	/**
	 * acctype以O_SALE结尾的表示国际的订单
	 */
	public final static String BASEACCOUNT_ACCTYPE_I="O_SALE";
	/**
	 * acctype以N_SALE结尾的表示国内的订单
	 */
	public final static String BASEACCOUNT_ACCTYPE_N="N_SALE";
	/**
	 * 	
	 * 517NA QUA QUNAR CTRIP TONGC 19E 51BOOK BAITOUR 8000YI QUNAR_O_SALE
	 * CTRIP_O_SALE TAOBAO_O_SALE KUXUN_O_SALE HBGJ_O_SALE TUNIU_O_SALE
	 * TAOBAO_N_SALE QUNAR_N_SALE TFARE_GDS
	 *
	 */
	/**
	 * 携程国际
	 */
	public final static String BASEACCOUNT_ACCTYPE_CTRIP_I="CTRIP_O_SALE";
	/**
	 * 淘宝国际
	 */
	public final static String BASEACCOUNT_ACCTYPE_TAOBAO_I="TAOBAO_O_SALE";
	/**
	 * 去哪国际
	 */
	public final static String BASEACCOUNT_ACCTYPE_QUNAR_I="QUNAR_O_SALE";
	/**
	 * 同程国际
	 */
	public final static String BASEACCOUNT_ACCTYPE_TONGC_I="TONGC_O_SALE";
	/**
	 * 途牛国际
	 */
	public final static String BASEACCOUNT_ACCTYPE_TUNIU_I="TUNIU_O_SALE";
	/**
	 * 携程国内
	 */
	public final static String BASEACCOUNT_ACCTYPE_CTRIP_N="CTRIP_N_SALE";
	/**
	 * 淘宝国内
	 */
	public final static String BASEACCOUNT_ACCTYPE_TAOBAO_N="TAOBAO_N_SALE";
	/**
	 * 去哪国内
	 */
	public final static String BASEACCOUNT_ACCTYPE_QUNAR_N="QUNAR_N_SALE";
	/**
	 * 同程国内
	 */
	public final static String BASEACCOUNT_ACCTYPE_TONGC_N="TONGC_N_SALE";
	/**
	 * 途牛国内
	 */
	public final static String BASEACCOUNT_ACCTYPE_TUNIU_N="TUNIU_N_SALE";
	/**
	 * 1表示启用
	 */
	public final static Integer BASEACCOUNT_ISU_ONE=1;
	/**
	 * 2表示禁用
	 */
	public final static Integer BASEACCOUNT_ISU_TWO=2;
	/**
	 * 1表示自动预定编码
	 */
	public final static Integer BASEACCOUNT_issd_one=1;
	/**
	 * 1 支付成功等待出票
	 */
	public final static String GjOrderGetListDomain_orderStatus_one="1";
	/**
	 * I表示国际的订单
	 */
	public final static String GjOrderGetListDomain_orderType_I="I";
	/**
	 * N表示国内的订单
	 */
	public final static String GjOrderGetListDomain_orderType_N="N";
	/**
	 * 2 出票完成
	 */
	public final static String GjOrderGetListDomain_orderStatus_two="2";
	/**
	 * 5 出票中
	 */
	public final static String GjOrderGetListDomain_orderStatus_five="5";

	/**
	 * 订单类型为国内的
	 */
	public final static String GjOrderGetDetailDomain_orderType_I="I";
	/**
	 * 订单类型为国际的
	 */
	public final static String GjOrderGetDetailDomain_orderType_N="N";
	/**
	 * 0表示成功
	 */
	public final static String GjOrderGetDetailResult_code_zero="0";
	/**
	 * 0表示成功,1表示失败
	 */
	public final static String SelectOrderDetailResult_code_zero="0";
	/**
	 * 0表示成功,1表示失败
	 */
	public final static String SelectOrderDetailResult_code_one="1";
	/**
	 * 0：不pat
	 */
	public final static Integer GetPnrInfoDomain_pat_zero=0;
	/**
	 * 1: pat
	 */
	public final static Integer GetPnrInfoDomain_pat_one=1;
	/**
	 * 0: 成功
	 */
	public final static Integer GetPnrInfoResult_code_zero=0;
	/**
	 * gds_cmd命令
	 */
	public final static String AllApi_service_GdsCmd="gds_cmd";
	/**
	 * gds_cmds命令（这个可以多个指令一起查询）
	 */
	public final static String AllApi_service_GdsCmds="gds_cmds";
	/**
	 * AllApi接口中，方法的service字段,囯内RT指令
	 */
	public final static String AllApi_service_GdsForRt="gds_for_rt";
	/**
	 * AllApi接口中，方法的service字段,囯内预定编码指令sd
	 */
	public final static String AllApi_service_GdsForSd="gds_for_sd";
	/**
	 * AllApi接口中，方法的service字段,bsp出票etdz
	 */
	public final static String AllApi_service_GdsForEtdz="gds_for_etdz";
	/**
	 * AllApi接口中，方法的service字段,囯内RT指令
	 */
	public final static String AllApi_service_GdsForAvh=" gds_for_avh";
	/**
	 * 查詢B2B政策信息
	 */
	public final static String AllApi_service_b2bPolicy="query_b2b_policys";
	/**
	 * 创建订单
	 */
	public final static String AllApi_service_b2bCreateOrder="b2b_create_order";
	/**
	 * 获取采购订单信息
	 */
	public final static String AllApi_service_getB2bOrderInfo="get_b2b_order_info";
	/**
	 * 支付订单
	 */
	public final static String AllApi_service_b2bPayOrder="pay_b2b_order";
	/**
	 * 锁单，或者解除锁定（对象是分销商的订单）
	 */
	public final static String AllApi_service_lockOrder="lock_order";
	/**
	 * 用于GDS查詢的url(黑屏指令地址)
	 */
	public final static String AllApi_url_GdsUrl="GDS_URL";
	/**
	 * 用于调用分销商的接口的url
	 */
	public final static String AllApi_url_CommUrl="COMM_URL";
	/**
	 * 信天游地址的url
	 */
	public final static String AllApi_url_XtyUrl="XTY_URL";
	/**
	 * 公共接口地址
	 */
	public final static String KEYVALUE_K_COMMURL="COMM_URL";
	/**
	 * 黑屏指令地址
	 */
	public final static String KEYVALUE_K_GDSURL="GDS_URL";
	/**
	 * 信天游地址
	 */
	public final static String KEYVALUE_K_XTYURL="XTY_URL";
	/**
	 * 乘客类别 -1 留学生 0 成人 1 儿童 2婴儿 -2其他
	 */
	public final static Integer GjSalePassengerEntity_ageType_adult=0;
	/**
	 * 乘客类别 -1 留学生 0 成人 1 儿童 2婴儿 -2其他
	 */
	public final static Integer GjSalePassengerEntity_ageType_child=1;
	/**
	 * ADU成人 CHD 儿童
	 */
	public final static String TakePeopleVO_psgType_adult="ADU";
	/**
	 * ADU成人 CHD 儿童
	 */
	public final static String TakePeopleVO_psgType_child="CHD";
	/**
	 * 订单状态 0 订座成功等待支付(人工) 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认（支付失败）
	 * 61 暂缓出票（出票失败） 62 订单超时 -1其它 99 订座成功等待支付（系统） 70退票留票中 80 审核未通过，拒绝退票 81
	 * 审核未通过，拒绝改签
	 */
	public final static String GjBuyOrderEntity_slfStatus_zero="0";
	/**
	 * 订单状态 0 订座成功等待支付(人工) 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认（支付失败）
	 * 61 暂缓出票（出票失败） 62 订单超时 -1其它 99 订座成功等待支付（系统） 70退票留票中 80 审核未通过，拒绝退票 81
	 * 审核未通过，拒绝改签
	 */
	public final static String GjBuyOrderEntity_slfStatus_one="1";
	/**
	 * 订单状态 0 订座成功等待支付(人工) 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认（支付失败）
	 * 61 暂缓出票（出票失败） 62 订单超时 -1其它 99 订座成功等待支付（系统） 70退票留票中 80 审核未通过，拒绝退票 81
	 * 审核未通过，拒绝改签
	 */	/**
	 *订单状态 0 订座成功等待支付 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认 61
	 * 暂缓出票 62 订单超时 -1其它
	 */
	public final static String GjBuyOrderEntity_slfStatus_two="2";
	/**
	 * 订单状态 0 订座成功等待支付(人工) 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认（支付失败）
	 * 61 暂缓出票（出票失败） 62 订单超时 -1其它 99 订座成功等待支付（系统） 70退票留票中 80 审核未通过，拒绝退票 81
	 * 审核未通过，拒绝改签
	 */
	public final static String GjBuyOrderEntity_slfStatus_five="5";
	/**
	 * 订单状态 0 订座成功等待支付(人工) 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认（支付失败）
	 * 61 暂缓出票（出票失败） 62 订单超时 -1其它 99 订座成功等待支付（系统） 70退票留票中 80 审核未通过，拒绝退票 81
	 * 审核未通过，拒绝改签
	 */
	public final static String GjBuyOrderEntity_slfStatus_twelve="12";
	/**
	 * 订单状态 0 订座成功等待支付(人工) 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认（支付失败）
	 * 61 暂缓出票（出票失败） 62 订单超时 -1其它 99 订座成功等待支付（系统） 70退票留票中 80 审核未通过，拒绝退票 81
	 * 审核未通过，拒绝改签
	 */
	public final static String GjBuyOrderEntity_slfStatus_sixty="60";
	/**
	 * 订单状态 0 订座成功等待支付(人工) 1 支付成功等待出票 2 出票完成 5 出票中 12 订单取消 20 等待座位确认 30 退票申请中 31
	 * 退票完成等待退款 39 退款完成 40 改签申请中 42 改签完成 50 未出票申请退款 51 订座成功等待价格确认 60 支付待确认（支付失败）
	 * 61 暂缓出票（出票失败） 62 订单超时 -1其它 99 订座成功等待支付（系统） 70退票留票中 80 审核未通过，拒绝退票 81
	 * 审核未通过，拒绝改签
	 */
	public final static String GjBuyOrderEntity_slfStatus_ninetynine="99";
	/**
	 * 1：可用
	 */
	public final static Long BookRuleEntity_isuse_enable=1L;
	/**
	 * 2:禁用
	 */
	public final static Long BookRuleEntity_isuse_disable=2L;
	/**
	 * 1表示启用
	 */
	public final static Integer BaseOfficeEntity_ISU_ONE=1;
	/**
	 * 2表示禁用
	 */
	public final static Integer BaseOfficeEntity_ISU_TWO=2;
	/**
	 * 用途 描述 RT,AV,PAT,FD,FLT,GDS,SD,ETDZ
	 */
	public final static String BaseOfficeEntity_offtypes_RT="RT";
	/**
	 * 用途 描述 RT,AV,PAT,FD,FLT,GDS,SD,ETDZ
	 */
	public final static String BaseOfficeEntity_offtypes_AV="AV";
	/**
	 * 用途 描述 RT,AV,PAT,FD,FLT,GDS,SD,ETDZ
	 */
	public final static String BaseOfficeEntity_offtypes_PAT="PAT";
	/**
	 * 用途 描述 RT,AV,PAT,FD,FLT,GDS,SD,ETDZ
	 */
	public final static String BaseOfficeEntity_offtypes_FD="FD";
	/**
	 * 用途 描述 RT,AV,PAT,FD,FLT,GDS,SD,ETDZ
	 */
	public final static String BaseOfficeEntity_offtypes_FLT="FLT";
	/**
	 * 用途 描述 RT,AV,PAT,FD,FLT,GDS,SD,ETDZ
	 */
	public final static String BaseOfficeEntity_offtypes_GDS="GDS";
	/**
	 * 用途 描述 RT,AV,PAT,FD,FLT,GDS,SD,ETDZ
	 */
	public final static String BaseOfficeEntity_offtypes_SD="SD";
	/**
	 * 用途 描述 RT,AV,PAT,FD,FLT,GDS,SD,ETDZ
	 */
	public final static String BaseOfficeEntity_offtypes_ETDZ="ETDZ";
	/**
	 * 成人
	 */
	public final static String PnrPassengerVO_psgType_adult="ADU";
	/**
	 * 兒童
	 */
	public final static String PnrPassengerVO_psgType_child="CHD";
	/**
	 * 0成功
	 */
	public final static Integer BuildPnrResult_code_zero=0;
	/**
	 * apply; // LL SA HK
	 */
	public final static String BuildPnrDomain_apply_LL="LL";
	public final static String BuildPnrDomain_apply_SA="SA";
	public final static String BuildPnrDomain_apply_HK="HK";
	
	
	// 1有底舱 2正常（考虑到以后要排序，有底舱的排到前面）
	public final static Integer GjSaleOrderEntity_isLowspace_ONE=1;
	public final static Integer GjSaleOrderEntity_isLowspace_TWO=2;
	//是否共享航班 Y:是,N:否 T其它
	public final static String GjSaleFlightEntity_isShared_Y="Y";
	//是否共享航班 Y:是,N:否 T其它
	public final static String GjSaleFlightEntity_isShared_N="N";
	//是否共享航班 Y:是,N:否 T其它
	public final static String GjSaleFlightEntity_isShared_T="T";
	/**
	 * ADU CHD
	 */
	public final static String AirPsgVO_psgType_ADU="ADU";
	/**
	 * ADU CHD
	 */
	public final static String AirPsgVO_psgType_CHD="CHD";
	/**
	 * SUCCESS FAIL
	 */
	public final static String CreateOrderForPb2bResult_executeStatus_SUCCESS="SUCCESS";
	/**
	 * SUCCESS FAIL
	 */
	public final static String CreateOrderForPb2bResult_executeStatus_FAIL="FAIL";
	
	/**
	 * 航班时刻表接口--Service
	 */
	public final static String FLIGHT_DYNAMICS = "query_hbdt";
	public final static String ProductDataEntity_productSource_LOCALBSP = "LOCALBSP";
	public final static String ProductDataEntity_productSource_517NA = "517NA";
	public final static String ProductDataEntity_productSource_8000YI = "8000YI";
	public final static Integer EtdzResult_code_zero = 0;
	/**
	 * 操作类型：1 代表认领，0 代表取消认领
	 */
	public final static  int LockOrderDomain_operatorType_one=1;
	/**
	 * 操作类型：1 代表认领，0 代表取消认领
	 */
	public final static  int LockOrderDomain_operatorType_zero=0;
	/**
	 * 成功
	 */
	public final static  String LockOrderResult_code_zero="0";
	/**
	 * C 采购 Z 支付
	 */
	public final static  String AccountManageEntity_accountType_purchase="C";
	/**
	 * C 采购 Z 支付
	 */
	public final static  String AccountManageEntity_accountType_pay="Z";
	/**
	 * 1:可用，2：禁用
	 */
	public final static  Integer AccountManageEntity_isu_enable=1;
	/**
	 * 1:可用，2：禁用
	 */
	public final static  Integer AccountManageEntity_isu_disable=2;
	public final static  String SysLogEntity_logType_ORDER_LOG="ORDER_LOG";
	/**
	 * 签到 1上班 2下班 3暂停
	 */
	public final static  Integer StaffWorkEntity_signIn_one=1;
	/**
	 * 签到 1上班 2下班 3暂停
	 */
	public final static  Integer StaffWorkEntity_signIn_two=2;
	/**
	 * 签到 1上班 2下班 3暂停
	 */
	public final static  Integer StaffWorkEntity_signIn_three=3;
	/**
	 * C：出票 T：退票 L:留票 Z:政策
	 */
	public final static  String StaffWorkEntity_workType_C="C";
	/**
	 * C：出票 T：退票 L:留票 Z:政策
	 */
	public final static  String StaffWorkEntity_workType_T="T";
	/**
	 * C：出票 T：退票 L:留票 Z:政策
	 */
	public final static  String StaffWorkEntity_workType_L="L";
	/**
	 * C：出票 T：退票 L:留票 Z:政策
	 */
	public final static  String StaffWorkEntity_workType_Z="Z";
	
	
}
