package com.smart.oo.dao.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.comm.entity.AirPriceEntity;
import com.smart.framework.base.BaseDAO;
import com.smart.framework.base.Page;
import com.smart.oo.dao.IAirPriceDao;
import com.smart.utils.StringUtils;

@Repository("BBOOAirPriceDaoImpl")
public class AirPriceDaoImpl extends BaseDAO<AirPriceEntity, String> implements
		IAirPriceDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3761302080808989988L;

	@Override
	public String saveData(AirPriceEntity en) throws Exception {
		// TODO Auto-generated method stub
		return this.save(en);
	}

	@Override
	public void del(AirPriceEntity entity) throws Exception {
		// TODO Auto-generated method stub
		this.executeSql(
				"delete from t_air_price where dep_code=? and arr_code=? and keys<? and keyid=?",
				new Object[] { entity.getDepCode(), entity.getArrCode(),
						entity.getKeys(), entity.getKeyID() });
	}

	@Override
	public List<AirPriceEntity> findDatas(AirPriceEntity en) throws Exception {
		// TODO Auto-generated method stub
		return this.find(getFindDataSQL(en));
	}

	private String getFindDataSQL(AirPriceEntity en) {
		StringBuffer buf = new StringBuffer("from AirPriceEntity where 1=1");
		if (en != null) {
			if (StringUtils.isNotEmpty(en.getCarrier())) {
				buf.append(" and carrier='" + en.getCarrier() + "'");
			}
			if (StringUtils.isNotEmpty(en.getCabin())) {
				buf.append(" and cabin='" + en.getCabin() + "'");
			}
			if (StringUtils.isNotEmpty(en.getDepCode())) {
				buf.append(" and depCode='" + en.getDepCode() + "'");
			}
			if (StringUtils.isNotEmpty(en.getArrCode())) {
				buf.append(" and arrCode='" + en.getArrCode() + "'");
			}
		}
		buf.append(" and IsDelete=0");
		buf.append(" order by carrier,depCode,arrCode,price desc,id desc");
		return buf.toString();
	}

	@Override
	public List<AirPriceEntity> findDatas(AirPriceEntity en, Page p)
			throws Exception {
		// TODO Auto-generated method stub
		return this.find(getFindDataSQL(en), p);
	}

	@Override
	public AirPriceEntity barrierById(String id) throws Exception {
		// TODO Auto-generated method stub
		List<AirPriceEntity> list = this.find("from AirPriceEntity where id=?",
				new Object[] { id });
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	
	
	/**
	 * 查询
	 */
	@Override
	public List<AirPriceEntity> queryAirPrice(AirPriceEntity entity)
			throws Exception {
		StringBuffer buf = new StringBuffer("from AirPriceEntity where 1=1");
		if (entity != null) {
			if (StringUtils.isNotEmpty(entity.getCarrier())) {
				buf.append(" and carrier='" + entity.getCarrier() + "'");
			}
			if (StringUtils.isNotEmpty(entity.getCabin())) {
				buf.append(" and cabin='" + entity.getCabin() + "'");
			}
			if (StringUtils.isNotEmpty(entity.getDepCode())) {
				buf.append(" and depCode='"+entity.getDepCode()+"'");
			}
			if (StringUtils.isNotEmpty(entity.getDepCode())) {
				buf.append(" and arrCode='"+entity.getArrCode()+"'");
			}
		}
		buf.append(" and isDelete=0");
		buf.append(" order by carrier,depCode,arrCode,price desc");
		return this.find(buf.toString());
	}
	/**
	 * 根据字段查询
	 */
	@Override
	public List<AirPriceEntity> queryByPage(AirPriceEntity entity, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("from AirPriceEntity where 1=1 ");
		if (StringUtils.isNotEmpty(entity.getCarrier())) {
			sb.append(" and carrier='" + entity.getCarrier() + "'");
		}
		if (StringUtils.isNotEmpty(entity.getCabin())) {
			sb.append(" and cabin='" + entity.getCabin() + "'");
		}
		if (StringUtils.isNotEmpty(entity.getDepCode())) {
			sb.append(" and depCode='"+entity.getDepCode()+"'");
		}
		if (StringUtils.isNotEmpty(entity.getArrCode())) {
			sb.append(" and arrCode='"+entity.getArrCode()+"'");
		}
		/*sb.append(" and isDelete=0");*/
		sb.append(" order by carrier,depCode,arrCode,price desc");
		return this.find(sb.toString(), page);
	}
	/**
	 * 添加保存
	 */
	@Override
	public void saveAirPrice(AirPriceEntity entity) throws Exception {
		// TODO Auto-generated method stub
		this.save(entity);
	}
	/**
	 * 删除
	 */
	@Override
	public void deleteAirPrice(String id) throws Exception {
		// TODO Auto-generated method stub
		this.delete(id);
	}
	/**
	 * 修改
	 */
	@Override
	public void updateAirPrice(AirPriceEntity entity) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("update AirPriceEntity ");
		sb.append("  set beginDate='"+entity.getBeginDate()+"'");
		sb.append(" , endDate='"+entity.getEndDate()+"'");
		sb.append(" , carrier='"+entity.getCarrier()+"'");
		sb.append(" , predaysStart='"+entity.getPredaysStart()+"'");
		sb.append(" , predaysEnd='"+entity.getPredaysEnd()+"'");
		sb.append(" , baseCabin='"+entity.getBaseCabin()+"'");
		sb.append(" , cabin='"+entity.getCabin()+"'");
		sb.append(" , depCode='"+entity.getDepCode()+"'");
		sb.append(" , arrCode='"+entity.getArrCode()+"'");
		sb.append(" , discount='"+entity.getDiscount()+"'");
		sb.append(" , basePrice='"+entity.getBasePrice()+"'");
		sb.append(" , price='"+entity.getPrice()+"'");
		sb.append(" , keys='"+entity.getKeys()+"'");
		sb.append(" , isDelete='"+entity.getIsDelete()+"'");
		sb.append(" where id="+entity.getId());
		this.executeHql(sb.toString());
	}
}
