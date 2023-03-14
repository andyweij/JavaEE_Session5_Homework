package com.training.action;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.training.formbean.BackActionForm;
import com.training.model.Goods;
import com.training.service.BackendService;
import com.training.vo.SalesReport;
@MultipartConfig
public class BackendAction extends DispatchAction{
	
	private BackendService backendservice = BackendService.getInstance();
	
	public ActionForward queryGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws IOException {
		List<Goods> goods = backendservice.queryGoods();
		goods.stream().forEach(a -> System.out.println(a.toString()));

		// Redirect to view
		return mapping.findForward("backendGoodsList");
	}

	public ActionForward updateGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 將表單資料轉換儲存資料物件
		BackActionForm backactionform=(BackActionForm)form;
		Goods good = new Goods();	
		BeanUtils.copyProperties(good, backactionform);
		boolean modifyResult = backendservice.modifyGood(good);
		String message = modifyResult ? "商品資料修改成功！" : "商品資料修改失敗！";
		System.out.println(message);
		// Redirect to view
		return mapping.findForward("backendGoodsReplenishment");
	}

	public ActionForward addGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 將表單資料轉換儲存資料物件		
		try {
			BackActionForm backactionform=(BackActionForm)form;
			Goods good = new Goods();
			BeanUtils.copyProperties(good, backactionform);
			FormFile file = backactionform.getGoodsImage();
			good.setGoodsImageName(file.getFileName());
			ServletContext application=this.getServlet().getServletContext();;
			String goodsImgPath=this.servlet.getInitParameter("GoodsImgPath");
			String serverGoodsImgPath =application.getRealPath(goodsImgPath);
			Path serverImgPath = Paths.get(serverGoodsImgPath).resolve(file.getFileName());
			try (InputStream fileContent =file.getInputStream() ;){
		        Files.copy(fileContent, serverImgPath, StandardCopyOption.REPLACE_EXISTING);
		    }
		boolean createResult = backendservice.createGood(good);
		String message = createResult ? "商品新增成功！" : "商品新增失敗！";
		System.out.println(message);
		}catch (Exception e) {
			e.printStackTrace();
		}
		// Redirect to view
		return mapping.findForward("backendGoodsCreate");
	}

	public ActionForward querySalesReport(ActionMapping mapping, ActionForm form, 
            HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BackActionForm backactionform=(BackActionForm)form;
		String queryStartDate = backactionform.getQueryStartDate();
		String queryEndDate = backactionform.getQueryEndDate();
		Set<SalesReport> salesreport = backendservice.querySalesReport(queryStartDate,queryEndDate);
		salesreport.stream().forEach(a -> System.out.println(a.toString()));

		// Redirect to view
		return mapping.findForward("backendGoodsSaleReport");
	}
}
