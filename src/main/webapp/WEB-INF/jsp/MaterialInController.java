package org.rnt.material.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rnt.com.GlvConst;
import org.rnt.com.controller.BaseController;
import org.rnt.com.entity.service.CompanyService;
import org.rnt.com.entity.service.MaterialInService;
import org.rnt.com.entity.service.MaterialOutService;
import org.rnt.com.entity.service.MaterialService;
import org.rnt.com.entity.service.StoreHouseService;
import org.rnt.com.entity.vo.CompanyVO;
import org.rnt.com.entity.vo.MaterialInVO;
import org.rnt.com.entity.vo.MaterialOutVO;
import org.rnt.com.entity.vo.MaterialVO;
import org.rnt.com.entity.vo.StoreHouseVO;
import org.rnt.com.file.service.ComFileService;
import org.rnt.com.service.ProPertyService;
import org.rnt.com.util.DateUtil;
import org.rnt.com.util.StrUtil;
import org.rnt.com.vo.RtnVO;
import org.rnt.material.service.MaterialMenuService;
import org.rnt.material.vo.MonthCloseInVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MaterialInController extends BaseController {

    protected Log log = LogFactory.getLog(this.getClass());

    @Resource(name="materialInService")
    private MaterialInService materialInService;

    @Resource(name="materialMenuService")
    private MaterialMenuService materialMenuService;

    @Resource(name="companyService")
    private CompanyService companyService;

    @Resource(name="storeHouseService")
    private StoreHouseService storeHouseService;

    @Resource(name="materialService")
    private MaterialService materialService;

    @Resource(name="proPertyService")
    private ProPertyService proPertyService;

    @Resource(name="comFileService")
    private ComFileService comFileService;

    @Resource(name="materialOutService")
    private MaterialOutService materialOutService;

    @RequestMapping(value = "/materialInListPage.do")
    public String materialInListPage(@ModelAttribute("search")MaterialInVO search, HttpServletRequest request, ModelMap model)  throws Exception {
        webStart(request);

        //---------------------------------------------------------------------
        // 날짜 조회 초기 값 처리
        //---------------------------------------------------------------------
        if (StrUtil.isNull(search.getSearchFromDate())) {
        	search.setSearchFromDate(DateUtil.formatCurrent("yyyyMM")+"01");
        	search.setSearchToDate(DateUtil.formatCurrent("yyyyMMdd"));
        } else {
        	search.setSearchFromDate(search.getSearchFromDate().replace("/", ""));
        	search.setSearchToDate(search.getSearchToDate().replace("/", ""));
        }

        //---------------------------------------------------------------------
        // paging set
        //---------------------------------------------------------------------
        if (StrUtil.isNull(search.getSortCol())) {
            search.setSortCol("UPDATE_DT");
            search.setSortType("DESC");
        }
        search.setPaging(true); // PageIndex >> FirstIndex, LastIndex
        RtnVO rtn = materialInService.searchList(search);
        RtnVO rtnTotCnt = materialInService.searchListTotCnt(search);
        rtn.setTotCnt((Integer)rtnTotCnt.getObj());

        CompanyVO company = new CompanyVO();
        company.setCustTypeCd("MAT"); // 자재처
        RtnVO selCompanyRtn = companyService.searchList(company);
        if (selCompanyRtn.getRc() == 0) {
            List<CompanyVO> companyList = (List<CompanyVO>)selCompanyRtn.getObj();
            model.addAttribute("mat_cust_list", companyList);
        }

        StoreHouseVO storeHouse = new StoreHouseVO();
        storeHouse.setSearchAreaCd("MAT_HOUSE");
        RtnVO selStoreHouseRtn = storeHouseService.searchList(storeHouse);
        if (selStoreHouseRtn.getRc() == 0) {
            List<StoreHouseVO> storeHouseList = (List<StoreHouseVO>)selStoreHouseRtn.getObj();
            model.addAttribute("store_house_list", storeHouseList);
        }

        //---------------------------------------------------------------------
        // 날짜 조회 조건  화면 format 변환
        //---------------------------------------------------------------------
        if (!StrUtil.isNull(search.getSearchFromDate())) {
        	search.setSearchFromDate(DateUtil.formatDateAsSlashFormat(search.getSearchFromDate()));
        	search.setSearchToDate(DateUtil.formatDateAsSlashFormat(search.getSearchToDate()));
        }

        getCode("BARCODE_PRINT", model);
        wedEnd(request, rtn, model);
        return "/material/materialInList";
    }

    @RequestMapping(value = "/materialInDtlPage.do")
    public String materialInDtlPage(@ModelAttribute("search")MaterialInVO search, HttpServletRequest request, ModelMap model)  throws Exception {
        webStart(request);
        RtnVO rtn = null;
        search.setFactoryCd(proPertyService.getFactoryCd());
        if ("R".equals(search.getCrudType())) {
            rtn = materialInService.select(search);
        } else {
            rtn = new RtnVO();
            MaterialInVO obj = new MaterialInVO();
            obj.setInDt(DateUtil.formatCurrent("yyyy/MM/dd"));
            rtn.setObj(obj);
        }

        CompanyVO company = new CompanyVO();
//        company.setCustTypeCd("MAT"); // 자재처
        RtnVO selCompanyRtn = companyService.searchList(company);
        if (selCompanyRtn.getRc() == 0) {
            List<CompanyVO> companyList = (List<CompanyVO>)selCompanyRtn.getObj();
            model.addAttribute("mat_cust_list", companyList);
        }

        StoreHouseVO storeHouse = new StoreHouseVO();
        storeHouse.setSearchAreaCd("MAT_HOUSE");
        RtnVO selStoreHouseRtn = storeHouseService.searchList(storeHouse);
        if (selStoreHouseRtn.getRc() == 0) {
            List<StoreHouseVO> storeHouseList = (List<StoreHouseVO>)selStoreHouseRtn.getObj();
            model.addAttribute("store_house_list", storeHouseList);
        }

        MaterialVO material = new MaterialVO();
        RtnVO selMaterialRtn = materialService.searchList(material);
        if (selMaterialRtn.getRc() == 0) {
            List<MaterialVO> materialList = (List<MaterialVO>)selMaterialRtn.getObj();
            model.addAttribute("material_list", materialList);
        }

        getCode("MAT_IN_TYPE_CD",model);
        getCode("UNIT_CD",model);

        wedEnd(request, rtn, model);
        return "/material/materialInDtl";
    }

    @RequestMapping(value = "/materialInSaveAct.do")
    public ModelAndView materialInSaveAct(@ModelAttribute("obj")MaterialInVO obj, HttpServletRequest request, ModelMap model)  throws Exception {
        webStart(request);
        ModelAndView mav = new ModelAndView("jsonView");
        RtnVO rtn = new RtnVO();

        if(log.isDebugEnabled()) {
            log.debug("getCrudType:"+obj.getCrudType());
            log.debug("getIndt:"+obj.getInDt());
        }

        obj.setFactoryCd(proPertyService.getFactoryCd());
        obj.setWriteId(getUserId(request));
        obj.setUpdateId(getUserId(request));

        // 수정시에만 체크
        if( !"C".equals(obj.getCrudType()) ) {
	        if( !"".equals(obj.getLotid()) ) {
		        MaterialOutVO paramMaterialOutVo = new MaterialOutVO();
		        paramMaterialOutVo.setFactoryCd(proPertyService.getFactoryCd());
		        paramMaterialOutVo.setLotid(obj.getLotid());
		        int rtnVal = materialOutService.checkMaterialOut(paramMaterialOutVo);

		        if ( rtnVal == -1 ) {
		        	wedEnd(request, rtn, mav);
		        	return mav;
		        } else if ( rtnVal > 0 ) {
		        	rtn.setRc(GlvConst.RC_ERROR);
		        	rtn.setMsg("자재출고 이후에는 입출고 할 수 없습니다.");
		        	wedEnd(request, rtn, mav);
		        	return mav;
		        }
	        }
        }
        if (!isMonthOpen(obj.getWorkshopCd(),obj.getInDt())) {
        	rtn.setRc(GlvConst.RC_ERROR);
        	rtn.setMsg("마감한 월은 입출고 할 수 없습니다.");
        	wedEnd(request, rtn, mav);
        	return mav;
        }

        if ("C".equals(obj.getCrudType())) {
        	rtn = materialInService.insert(obj);
        } else if ("U".equals(obj.getCrudType())) {
            rtn = materialInService.update(obj);
        } else if ("D".equals(obj.getCrudType())) {
            rtn = materialInService.delete(obj);
        } else {
            rtn.setRc(GlvConst.RC_ERROR);
            rtn.setMsg("알수없는 저장 타입 :"+obj.getCrudType());
        }

        wedEnd(request, rtn, mav);
        return mav;
    }

    @RequestMapping(value = "/getMaterialInDataByLotId.do")
    public ModelAndView getMaterialInDataByLotId(@ModelAttribute("search")MaterialInVO search, HttpServletRequest request, ModelMap model)  throws Exception {
        webStart(request);
        ModelAndView mav = new ModelAndView("jsonView");
        search.setFactoryCd(proPertyService.getFactoryCd());
        RtnVO rtn = materialInService.selectByLotId(search);
        wedEnd(request, rtn, mav);
        return mav;
    }

    public boolean isMonthOpen(String workshopCd, String indt) {
        MonthCloseInVO param = new MonthCloseInVO();
        param.setWorkshopCd(workshopCd);
        param.setMagamYyyymm(indt);
        RtnVO rtn = materialMenuService.selectMaxCloseMonthAndDiffMonth(param);

        if (rtn.getRc() == GlvConst.RC_SUCC) {
            Integer diffMonth = (Integer)rtn.getObj();
            if (diffMonth != null) {
                if (diffMonth > 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/selectPopMaterialInList.do")
    public ModelAndView selectPopMaterialInList(@ModelAttribute("search")MaterialInVO search, HttpServletRequest request, ModelMap model)  throws Exception {
        webStart(request);
        ModelAndView mav = new ModelAndView("jsonView");

        //---------------------------------------------------------------------
        // 날짜 조회 초기 값 처리
        //---------------------------------------------------------------------

        if (StrUtil.isNull(search.getSearchToDate())) {
        	search.setSearchToDate(DateUtil.formatCurrent("yyyyMMdd"));
        } else {
        	search.setSearchToDate(search.getSearchToDate().replace("/", ""));
        }

        search.setFactoryCd(proPertyService.getFactoryCd());
        RtnVO rtn = materialInService.selectPopMaterialInList(search);

        //---------------------------------------------------------------------
        // 날짜 조회 조건  화면 format 변환
        //---------------------------------------------------------------------
        if (!StrUtil.isNull(search.getSearchToDate())) {
        	search.setSearchToDate(DateUtil.formatDateAsSlashFormat(search.getSearchToDate()));
        }

        wedEnd(request, rtn, mav);
        return mav;
    }

    @RequestMapping(value = "/mobileMerterialInData.do")
    public ModelAndView merterialInData(@ModelAttribute("search")MaterialInVO search, HttpServletRequest request, ModelMap model)  throws Exception {
        webStart(request);
        ModelAndView mav = new ModelAndView("jsonView");

        search.setSearchStockYn("Y");
//        RtnVO rtn = materialInService.searchList(search);
        RtnVO rtn = materialInService.searchMaterialStockList(search);

        wedEnd(request, rtn, mav);
        return mav;
    }

}
