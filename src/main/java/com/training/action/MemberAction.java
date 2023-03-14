package com.training.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.training.dao.FrontEndDao;
import com.training.formbean.GoodsOrderForm;
import com.training.model.Goods;

@MultipartConfig
public class MemberAction extends DispatchAction {

	private static FrontEndDao frontenddao = FrontEndDao.getInstance();

	public ActionForward addCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		GoodsOrderForm cartgoods = (GoodsOrderForm) form;
		String goodsID = req.getParameter("goodsID");
		String buyQuantity = req.getParameter("buyQuantity");
		System.out.println("goodsID:" + goodsID);
		System.out.println("buyQuantity:" + buyQuantity);
		// 查詢資料庫商品並且加入購物車
		Map<Goods, Integer> carGoods;
		Goods goods = frontenddao.queryByGoods(goodsID);
		HttpSession session = req.getSession();
		if (session.getAttribute("carGoods") == null) {
			carGoods = new LinkedHashMap<>();
			carGoods.put(goods, Integer.parseInt(buyQuantity));
		} else {
			carGoods = (LinkedHashMap) session.getAttribute("carGoods");
			if(carGoods.containsKey(goods)) {
				carGoods.replace(goods, Integer.parseInt(buyQuantity)+carGoods.get(goods));
			}else {
			carGoods.put(goods, Integer.parseInt(buyQuantity));
			}
		}
		session.setAttribute("carGoods", carGoods);
		return mapping.findForward("vendingMachine");
	}

	public ActionForward queryCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		Map<Goods, Integer> carGoods=(LinkedHashMap)session.getAttribute("carGoods");
		if(null==carGoods) {
			System.out.println("購物車無選購商品");
		}else {
			System.out.println("-----購物車商品-----");
		carGoods.keySet().stream().forEach(g->System.out.println("商品名稱:"+g.getGoodsName()+"\n商品價格:"+g.getGoodsPrice()+"\n數量:"+carGoods.get(g))
				);
		}
		return mapping.findForward("vendingMachine");
	}

	public ActionForward clearCartGoods(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		System.out.println("購物車已清空!");
		session.removeAttribute("carGoods");	
		return mapping.findForward("vendingMachine");
	}
}
