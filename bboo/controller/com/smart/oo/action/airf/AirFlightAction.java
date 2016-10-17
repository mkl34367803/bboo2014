package com.smart.oo.action.airf;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import com.smart.comm.entity.AirFlightEntity;
import com.smart.comm.entity.BaseOfficeEntity;
import com.smart.comm.entity.KeyValEntity;
import com.smart.comm.utils.OOComms;
import com.smart.comm.utils.OOLogUtil;
import com.smart.comm.utils.OOUtils;
import com.smart.framework.base.BaseAction;
import com.smart.framework.base.Page;
import com.smart.oo.domain.GetFlightByLineDomain;
import com.smart.oo.service.imp.FactoryServiceImpl;
import com.smart.timer.base.DateFormat;
import com.smart.utils.DateUtils;
import com.smart.utils.StringUtils;




@Results({ @Result(name = "trigger", location = "/jsp/build/airf/trigger.jsp") })
public class AirFlightAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7298682902135697818L;
	@Autowired
	private FactoryServiceImpl factoryService;
	private static Integer LINECOUNT = 0;
	private static Integer OKCOUNT = 0;

	public String toPage() {
		request.setAttribute("msg", "航段总数：" + LINECOUNT + ";初始化执行：" + OKCOUNT
				+ ";还剩余：" + (LINECOUNT - OKCOUNT));
		return "trigger";
	}

	private List<AirFlightEntity> getShareList() {
		List<AirFlightEntity> enlist = null;
		try {
			enlist = factoryService.getAirFlightService().findShares(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enlist;
	}

	public void loadShareFlt() {
		List<AirFlightEntity> enlist = getShareList();
		String filePath = com.smart.comm.utils.FileUtils.getFilePath(
				"jsp/files/share", "share_" + System.currentTimeMillis());
		com.smart.comm.utils.FileUtils.writeHeader(filePath, "航司", "航班号", "舱位",
				"实际承运", "实际承运航班号", "起飞机场", "到达城市");
		StringBuffer bodyBuf = new StringBuffer();
		if (enlist != null && enlist.size() > 0) {
			Iterator<AirFlightEntity> iterator = enlist.iterator();
			while (iterator.hasNext()) {
				AirFlightEntity vo = iterator.next();
				String cabins = vo.getCabins();
				if (cabins != null) {
					String[] cwarr = cabins.split(",");
					for (String c : cwarr) {
						com.smart.comm.utils.FileUtils.writeBody(
								bodyBuf,
								vo.getAir(),
								vo.getFno(),
								c,
								vo.getSno().length() > 2 ? (vo.getSno()
										.replace("*", "").trim()
										.substring(0, 2)) : "", vo.getSno(), vo
										.getDep(), vo.getArr());

					}
					cwarr = null;
				}
				iterator.remove();
			}
		}
		com.smart.comm.utils.FileUtils.xiefile(bodyBuf, filePath);
		String zipFilePath = com.smart.comm.utils.FileUtils.writeEnd(filePath);
		zipFilePath = "jsp" + zipFilePath.split("jsp")[1];
		this.writeStream("<a href='../" + zipFilePath
				+ "' name='share_loads_id001x'>" + "点击这下载./" + zipFilePath
				+ "</a>", "utf-8");
	}

	public void init() {

		List<KeyValEntity> keyvals = null;
		try {
			keyvals = factoryService.getIkeyValService().findDataList(
					OOComms.CACHE_KEY_VAL_DATAS_KEY, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		KeyValEntity val = null;
		if (keyvals != null && keyvals.size() > 0) {
			for (KeyValEntity v : keyvals) {
				if (OOComms.GET_API_COMM_URL.equalsIgnoreCase(v.getK())) {
					val = v;
					break;
				}
			}
		}
		if (val == null) {
			return;
		}
		List<BaseOfficeEntity> offs = null;
		try {
			offs = factoryService.getBaseOfficeService().findDataList(
					OOComms.CACHE_OFF_VAL_DATAS_NAME,
					OOComms.CACHE_OFF_VAL_DATAS_KEY, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BaseOfficeEntity off = null;
		if (offs != null && offs.size() > 0) {
			for (BaseOfficeEntity b : offs) {
				if (b.getOfftypes() != null && 1 == b.getIsu()
						&& b.getOfftypes().toUpperCase().contains("AV")) {
					off = b;
					break;
				}
			}
		}
		if (off == null) {
			return;
		}
		// 得到全国航线
		List<String> lines = null;
		try {
			lines = OOUtils.readFileByLine(OOUtils.getFileUri("txt",
					"lines.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lines == null || lines.size() == 0) {
			return;
		}
		String depDate = DateUtils.formatDate(DateUtils.addDate(new Date(), 7));
		LINECOUNT = lines.size();
		OKCOUNT = 0;
		for (String l : lines) {
			String[] shuz = l.trim().split("	");
			if (shuz.length > 1) {
				String depCode = shuz[0].trim();
				String arrCode = shuz[1].trim();
				GetFlightByLineDomain line = new GetFlightByLineDomain();
				line.setDepCode(depCode.trim().toUpperCase());
				line.setArrCode(arrCode.trim().toUpperCase());
				line.setDepDate(depDate);
				factoryService.getAirFlightService().saveDatas(line, off, val);
			}
			OKCOUNT++;
		}
	}
	
	
	/**
	 * 查询
	 */
	public void queryByPage() {
		try {
			String startPage = request.getParameter("page");
			String rows = request.getParameter("rows");
			String air=request.getParameter("air");
			String arr=request.getParameter("arr");
			String arrt=request.getParameter("arrt");
			String cabins=request.getParameter("cabins");
			String ct=request.getParameter("ct");
			String dep=request.getParameter("dep");
			String dept=request.getParameter("dept");
			String fno=request.getParameter("fno");
			String indexkey=request.getParameter("indexkey");
			String sno=request.getParameter("sno");
			String istop=request.getParameter("istop");
			String inat=request.getParameter("inat");
			String isu=request.getParameter("isu");
			Page page = null;
			if (StringUtils.isNotEmpty(startPage) && StringUtils.isNotEmpty(rows)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startPage));
				page.setPageSize(Integer.parseInt(rows));
			}
			AirFlightEntity entity = new AirFlightEntity();
			entity.setAir(air);
			entity.setArr(arr);
			entity.setArrt(arrt);
			entity.setCabins(cabins);
			entity.setCt(ct);
			entity.setDep(dep);
			entity.setDept(dept);
			entity.setFno(fno);
		    entity.setIndexkey(indexkey);
			entity.setIstop(istop);
			entity.setSno(sno);
			if (StringUtils.isNotEmpty(inat)) {
				entity.setInat(Integer.parseInt(inat));
			}
			if (StringUtils.isNotEmpty(isu)) {
				entity.setIsu(Integer.parseInt(isu));
			}
			List<AirFlightEntity> airFlightList = this.factoryService.getAirFlightService().queryByPage(entity, page);
			/*转json*/
			JSONObject result = new JSONObject();
			if (airFlightList != null && airFlightList.size() > 0) {
				String list = JSON.toJSONString(airFlightList);
				result.put("list", list);
			} else {
				result.put("list", "[]");
			}
			result.put(SUCCESS, "查询成功");
			result.put("total", page.getTotalCount());
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	/**
	 * 修改添加
	 */
	public void saveOrUpdate() {
		try {
			
			String id=request.getParameter("id");
			String startPage = request.getParameter("page");
			String rows = request.getParameter("rows");
			String air=request.getParameter("air");
			String arr=request.getParameter("arr");
			String arrt=request.getParameter("arrt");
			String cabins=request.getParameter("cabins");
			String dep=request.getParameter("dep");
			String dept=request.getParameter("dept");
			String fno=request.getParameter("fno");
			String indexkey=request.getParameter("indexkey");
			String sno=request.getParameter("sno");
			String istop=request.getParameter("istop");
			String inat=request.getParameter("inat");
			String isu=request.getParameter("isu");
			Page page = null;
			if (StringUtils.isNotEmpty(startPage) && StringUtils.isNotEmpty(rows)) {
				page = new Page();
				page.setStartPage(Integer.parseInt(startPage));
				page.setPageSize(Integer.parseInt(rows));
			}
			AirFlightEntity entity = new AirFlightEntity();
			entity.setAir(air);
			entity.setArr(arr);
			entity.setArrt(arrt);
			entity.setCabins(cabins);
			entity.setDep(dep);
			entity.setDept(dept);
			entity.setFno(fno);
		    entity.setIndexkey(indexkey);
			entity.setIstop(istop);
			entity.setSno(sno);
			if (StringUtils.isNotEmpty(inat)) {
				entity.setInat(Integer.parseInt(inat));
			}
			if (StringUtils.isNotEmpty(isu)) {
				entity.setIsu(Integer.parseInt(isu));
			}if (StringUtils.isNotEmpty(id)) {
				entity.setId(Integer.parseInt(id));
			}
			JSONObject result = new JSONObject();
			if (StringUtils.isNotEmpty(id)) {
				this.factoryService.getAirFlightService().updateAirFlight(entity);
				result.put(SUCCESS, "修改成功");
				info("AirFlight修改成功, id 为"+id);
			} else {
				entity.setCt(DateFormat.getStandardSysdate());//系统时间
				this.factoryService.getAirFlightService().saveAirFlight(entity);
				result.put(SUCCESS, "添加成功");
				info("AirFlight添加成功");
			}
			this.writeStream(result, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put(ERROR, e.toString());
			this.writeStream(result, "utf-8");
		}
	}
	private void info(String msg) {
		OOLogUtil.info(msg, AirFlightAction.class, null);
	}
	
}
