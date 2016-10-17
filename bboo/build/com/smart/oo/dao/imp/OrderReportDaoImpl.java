package com.smart.oo.dao.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.comm.entity.GjSaleOrderEntity;
import com.smart.comm.utils.DateUtil;
import com.smart.comm.utils.StringUtilsc;
import com.smart.framework.base.BaseDAO;
import com.smart.framework.base.Page;
import com.smart.oo.dao.IOrderReportDao;
import com.smart.oo.from.OrderReportVo;
@Repository("BBOOOrderReportDaoImpl")
public class OrderReportDaoImpl extends BaseDAO<GjSaleOrderEntity, String> implements IOrderReportDao {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Object[]> getSaleOrderList(OrderReportVo vo, OrderReportVo voDate) throws Exception {

		StringBuffer sb = new StringBuffer();
		
		sb.append("select ");
		// BuyOrder表查询字段
		sb.append(" bo.id boid,bo.ext_orderid,bo.purchase_no,bo.old_order_no,bo.flight_type,bo.airline_count," +
				"bo.passenger_count,bo.bx_fee as bo_box_fee,bo.bx_count as bo_bx_count, bo.pnr_code as buy_pnr_code," +
				"bo.pnr_code_big as bo_pnr_code_big,bo.pay_way bo_pay_way,bo.transaction_no,bo.create_time,bo.ticket_date," +
				"bo.flight_class,bo.purchase_place, bo.purchase_place_ch,bo.supplier,bo.cabin_des," +
				"bo.back_point,bo.after_point,bo.sticking_point,bo.reward,bo.sale_price,bo.lr,bo.allprice as bo_allprice," +
				"bo.slf_remark,bo.merchant_no as bo_merchant_no,bo.status,bo.slf_status,bo.pay_account,bo.remark bo_remark ");
		sb.append(",");
		// BuyPassenter表查询字段
		sb.append("bp.id as bp_id,bp.name,bp.gender,bp.age_type,bp.age_des,bp.eticket_num,bp.print_price as bp_print_price," +
				"bp.oil_fee as bp_oil_free,bp.tax_fee as bp_tax_fee,bp.pay_pirce as bp_pay_pirce,bp.cost as bp_cost ");
		sb.append(",");
		// SaleOrder表查询字段
		sb.append("so.ext_oid,so.order_no,so.policy_type,so.adult_price,so.adult_tax,so.child_tax,so.child_price,so.bx_fee as so_bx_fee," +
				"so.pnr_code as so_pnr_code,so.pnr_code_big as so_pnr_code_big,so.bill_id,so.shop_name,so.shop_name_ch," +
				"so.distributor,so.distributor_ch,so.rt_offno,so.open_status,so.open_des,so.buy_price,so.policy_id,so.policy_code," +
				"so.product_source,so.operator,so.status as so_status,so.slf_status as so_slf_status,so.old_pnr_code,so.old_allprice as so_old_allprice,so.allprice," +
				"so.account_info,so.mno,so.journey_sheet_price,so.lock_user,so.statement,so.pay_way so_pay_way,so.remark so_remark,so.slf_remark so_slf_remark ");
		sb.append(",");
		// SalePassenger表查询字段
		sb.append("sp.bx_count,sp.ins_no,sp.cost,sp.oil_fee as sp_oil_fee,sp.tax_fee as sp_tax_fee,sp.price,sp.commission," +
				"sp.print_price as sp_print_price,sp.price as sp_price ");
		sb.append(",");
		// SaleFlight表查询字段
		sb.append("sf.sequence,sf.carrier,sf.air_code_ch,sf.flight_num,sf.cabin,sf.dep_aircode,sf.dep_aircode_ch,sf.arr_aircode," +
				"sf.arr_aircode_ch,sf.departure_date,sf.departure_time,sf.arrival_date,sf.arrival_time,sf.segment_type,sf.add_time," +
				"sf.self_print_price ");
		sb.append(",");
		sb.append("bf.cabin ");
		sb.append("from t_gj_saleorder so ");
		sb.append(" inner join t_gj_saleflight sf on so.id=sf.fkid  ");
		sb.append(" inner join t_gj_salepassenger sp on so.id=sp.fkid ");
		sb.append(" inner join t_gj_buyorder bo on bo.id=so.id ");
		sb.append(" inner join t_gj_buyflight bf on bo.id=bf.fkid and bf.id=sf.id ");
		sb.append(" inner join t_gj_buypassenger bp on bo.id=bp.fkid and bp.id=sp.id ");
		
		sb.append(" where 1=1");
		if(StringUtilsc.isNotEmpty(vo.getDistributor())){
			sb.append(" and so.distributor = '"+vo.getDistributor()+"'");
		}
		if (voDate != null) {
			if(StringUtilsc.isNotEmpty(vo.getCreateTime()) && StringUtilsc.isNotEmpty(voDate.getCreateTime())){
				sb.append(" and so.create_time >= '"+vo.getCreateTime()+"' and so.create_time < '"+DateUtil.stringDatePlusOne(voDate.getCreateTime())+"'");
			}
			if(StringUtilsc.isNotEmpty(vo.getTicketDate()) && StringUtilsc.isNotEmpty(voDate.getTicketDate())){
				sb.append(" and so.slf_addtime >= '"+vo.getTicketDate()+"' and so.slf_addtime < '"+DateUtil.stringDatePlusOne(voDate.getTicketDate())+"'");
			}
			if(StringUtilsc.isNotEmpty(vo.getDepartureDate()) && StringUtilsc.isNotEmpty(voDate.getDepartureDate())){
				sb.append(" and sf.departure_date >= '"+vo.getDepartureDate()+"' and sf.departure_date < '"+DateUtil.stringDatePlusOne(voDate.getDepartureDate())+"'");
			}
		}
		if(StringUtilsc.isNotEmpty(vo.getFlightClass())){
			sb.append(" and so.flight_class = '"+vo.getFlightClass()+"'");
		}
		
		if(StringUtilsc.isNotEmpty(vo.getBoAddtime()) && StringUtilsc.isNotEmpty(voDate.getBoAddtime())){
			sb.append(" and bo.addtime >= '"+vo.getBoAddtime()+"' and bo.addtime < '"+DateUtil.stringDatePlusOne(voDate.getBoAddtime())+"'");
		}
		sb.append(" order by bo.id,bp.id");
		return this.findBySQL(sb.toString());
	}

	@Override
	public List<Object[]> getSaleOrderListByPage(OrderReportVo vo,
			OrderReportVo voDate, Page page) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select ");
		// BuyOrder表查询字段
		sb.append(" bo.id boid,bo.ext_orderid,bo.purchase_no,bo.old_order_no,bo.flight_type,bo.airline_count," +
				"bo.passenger_count,bo.bx_fee as bo_box_fee,bo.bx_count as bo_bx_count, bo.pnr_code as buy_pnr_code," +
				"bo.pnr_code_big as bo_pnr_code_big,bo.pay_way bo_pay_way,bo.transaction_no,bo.create_time,bo.ticket_date," +
				"bo.flight_class,bo.purchase_place, bo.purchase_place_ch,bo.supplier,bo.cabin_des," +
				"bo.back_point,bo.after_point,bo.sticking_point,bo.reward,bo.sale_price,bo.lr,bo.allprice as bo_allprice," +
				"bo.slf_remark,bo.merchant_no as bo_merchant_no,bo.status,bo.slf_status,bo.pay_account,bo.remark bo_remark ");
		sb.append(",");
		// BuyPassenter表查询字段
		sb.append("bp.id as bp_id,bp.name,bp.gender,bp.age_type,bp.age_des,bp.eticket_num,bp.print_price as bp_print_price," +
				"bp.oil_fee as bp_oil_free,bp.tax_fee as bp_tax_fee,bp.pay_pirce as bp_pay_pirce,bp.cost as bp_cost ");
		sb.append(",");
		// SaleOrder表查询字段
		sb.append("so.ext_oid,so.order_no,so.policy_type,so.adult_price,so.adult_tax,so.child_tax,so.child_price,so.bx_fee as so_bx_fee," +
				"so.pnr_code as so_pnr_code,so.pnr_code_big as so_pnr_code_big,so.bill_id,so.shop_name,so.shop_name_ch," +
				"so.distributor,so.distributor_ch,so.rt_offno,so.open_status,so.open_des,so.buy_price,so.policy_id,so.policy_code," +
				"so.product_source,so.operator,so.status as so_status,so.slf_status as so_slf_status,so.old_pnr_code,so.old_allprice as so_old_allprice,so.allprice," +
				"so.account_info,so.mno,so.journey_sheet_price,so.lock_user,so.statement,so.pay_way so_pay_way,so.remark so_remark,so.slf_remark so_slf_remark ");
		sb.append(",");
		// SalePassenger表查询字段
		sb.append("sp.bx_count,sp.ins_no,sp.cost,sp.oil_fee as sp_oil_fee,sp.tax_fee as sp_tax_fee,sp.price,sp.commission," +
				"sp.print_price as sp_print_price,sp.price as sp_price ");
		sb.append(",");
		// SaleFlight表查询字段
		sb.append("sf.sequence,sf.carrier,sf.air_code_ch,sf.flight_num,sf.cabin,sf.dep_aircode,sf.dep_aircode_ch,sf.arr_aircode," +
				"sf.arr_aircode_ch,sf.departure_date,sf.departure_time,sf.arrival_date,sf.arrival_time,sf.segment_type,sf.add_time," +
				"sf.self_print_price ");
		sb.append(",");
		sb.append("bf.cabin ");
		sb.append("from t_gj_saleorder so ");
		sb.append(" inner join t_gj_saleflight sf on so.id=sf.fkid  ");
		sb.append(" inner join t_gj_salepassenger sp on so.id=sp.fkid ");
		sb.append(" inner join t_gj_buyorder bo on bo.id=so.id ");
		sb.append(" inner join t_gj_buyflight bf on bo.id=bf.fkid and bf.id=sf.id ");
		sb.append(" inner join t_gj_buypassenger bp on bo.id=bp.fkid and bp.id=sp.id ");
		
		sb.append(" where 1=1");
		if(StringUtilsc.isNotEmpty(vo.getDistributor())){
			sb.append(" and so.distributor = '"+vo.getDistributor()+"'");
		}
		if (voDate != null) {
			if(StringUtilsc.isNotEmpty(vo.getCreateTime()) && StringUtilsc.isNotEmpty(voDate.getCreateTime())){
				sb.append(" and so.create_time >= '"+vo.getCreateTime()+"' and so.create_time < '"+DateUtil.stringDatePlusOne(voDate.getCreateTime())+"'");
			}
			if(StringUtilsc.isNotEmpty(vo.getTicketDate()) && StringUtilsc.isNotEmpty(voDate.getTicketDate())){
				sb.append(" and so.slf_addtime >= '"+vo.getTicketDate()+"' and so.slf_addtime < '"+DateUtil.stringDatePlusOne(voDate.getTicketDate())+"'");
			}
			if(StringUtilsc.isNotEmpty(vo.getDepartureDate()) && StringUtilsc.isNotEmpty(voDate.getDepartureDate())){
				sb.append(" and sf.departure_date >= '"+vo.getDepartureDate()+"' and sf.departure_date < '"+DateUtil.stringDatePlusOne(voDate.getDepartureDate())+"'");
			}
		}
		if(StringUtilsc.isNotEmpty(vo.getFlightClass())){
			sb.append(" and so.flight_class = '"+vo.getFlightClass()+"'");
		}
		
		if(StringUtilsc.isNotEmpty(vo.getBoAddtime()) && StringUtilsc.isNotEmpty(voDate.getBoAddtime())){
			sb.append(" and bo.addtime >= '"+vo.getBoAddtime()+"' and bo.addtime < '"+DateUtil.stringDatePlusOne(voDate.getBoAddtime())+"'");
		}
		sb.append(" order by bo.id,bp.id");
		return this.findBySQL(sb.toString(), page);
	}




}
