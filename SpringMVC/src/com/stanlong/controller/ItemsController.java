package com.stanlong.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.stanlong.exception.CustomerException;
import com.stanlong.pojo.ItemsCustomer;
import com.stanlong.pojo.ItemsQueryVo;
import com.stanlong.service.ItemsService;
import com.stanlong.validate.ValidateGroup1;

@Controller
@RequestMapping("/items")
public class ItemsController {

	@Autowired
	private ItemsService itemsService;
	
	//第一种方式返回ModelAndView
	@RequestMapping("/queryItems5")
	public ModelAndView queryItemsList(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception{
		List<ItemsCustomer> itemList = itemsService.queryItemList(itemsQueryVo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemList", itemList);
		modelAndView.setViewName("items/itemList");
		return modelAndView;
		
	}
	
	//第二种方式返回String:表示返回的逻辑视图名，真正的视图（jsp路径）= 前缀+逻辑视图名+后缀
	/*@RequestMapping(value = "/queryItems5")
	public String queryItemsList(Model model) throws Exception{
		List<ItemsCustomer> itemList = itemsService.queryItemList(null);
		model.addAttribute("itemList", itemList);
		return "items/itemList";
	}*/
	
	//数据回显，商品分类 ，这种写法好像在哪个页面上都可以回显，了解即可。
	// @ModelAttribute 可以指定pojo回显到页面，在request中的key
	@ModelAttribute("itemsType")
	public Map<String, String> getItemsType(){
		Map<String, String> itemsType = new HashMap<>();
		itemsType.put("101", "数码");
		itemsType.put("102", "母婴");
		return itemsType;
	}
	
	//限制http的请求方法，可以POST, 也可以 GET
	@RequestMapping(value = "/editItems", method={RequestMethod.POST, RequestMethod.GET})
	//通过@RequestParam对简单类型的参数进行绑定， 如果不使用@RequestParam，要求request传入参数名和controller方法的形参名称一致，方可绑定成功。
	//如果名称不一致会报 "Source must not be null" 错误
	//required=true 该参数为必传。 defaultValue必传
	public ModelAndView editItems(@RequestParam(value="id", required=true, defaultValue="") Integer itemId) throws Exception{
		ItemsCustomer itemsCustomer = new ItemsCustomer();
		itemsCustomer = itemsService.findItemById(itemId);
		//
		if(null == itemsCustomer){
			throw new CustomerException("修改的商品信息不存在");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsCustomer", itemsCustomer);
		modelAndView.setViewName("items/editItems");
		return modelAndView;
	}
	
	//在需要校验的pojo前边添加@Validated, 在需要校验的pojo后面添加 BindingResult 来接收校验出错的信息
	//注 @Validated 和 BindingResult 是配对出现的，且位置固定
	// value={ValidateGroup1.class} r指定使用 ValidateGroup1 的分组校验
	@RequestMapping("/editItemsSubmit")
	public String editItemsSubmit(Model model, 
			HttpServletRequest request, Integer id,
			@Validated(value={ValidateGroup1.class}) ItemsCustomer itemsCustomer,
			BindingResult bindingResult,
			MultipartFile itemsPic) throws Exception{
		//获取检验错误信息
		if(bindingResult.hasErrors()){
			//输出错误信息
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			//直接传 ${allErrors.defaultMessage} 在页面上展示乱码，这里先转成字符串列表，再传到页面上
			List<String> errMessages = new ArrayList<>(); 
			for(ObjectError objectError : allErrors){
				String errMessage = new String(objectError.getDefaultMessage().getBytes("ISO-8859-1"), "UTF-8");
				errMessages.add(errMessage);
			}
			model.addAttribute("errMessages", errMessages);
			//如果修改的内容没有通过验证，则重新回到商品修改页面。
			return "items/editItems";
		}
		
		//上传图片
		if(itemsPic != null){
			//原始图片名称
			String oldPicName = itemsPic.getOriginalFilename();
			if(null != oldPicName && oldPicName.length()>0){
				//存储图片的物理路径
				String picPath = "D:\\Program Files\\apache-tomcat-7.0.81\\temp\\";
				
				//新的图片名称
				String newPicName = UUID.randomUUID() + oldPicName.substring(oldPicName.lastIndexOf("."));
				//新的图片
				File file = new File(picPath+newPicName);
				//将内存中的数据写入磁盘
				itemsPic.transferTo(file);
				//将新的图片名称写入到 itemsCustomer 中
				itemsCustomer.setPic(newPicName);
			}
		}
		
		itemsService.updateItemById(id, itemsCustomer);
		//重定向: 浏览器地址栏中的url会变化，修改提交的request数据无法传到重定向的地址，因为重定向后重新进行request(request无法共享)
		//return "redirect:queryItems5.action";
		
		//页面转发:浏览器地址栏中的url不变， request可以共享
		return "forward:queryItems5.action";
	}
	
	@RequestMapping("/deleteItems")
	public String deleteItems(Integer[] itemIds) throws Exception{
		//items表有外建约束，不能直接删除id， 这里直接打印出 itemIds
		System.out.println("--------------数组参数绑定--------------");
		return "forward:queryItems5.action";
	}
	
	//批量修改商品的页面(List参数绑定)
	@RequestMapping("/editItemsListQuery")
	public ModelAndView editItemsListQuery(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception{
		List<ItemsCustomer> itemList = itemsService.queryItemList(itemsQueryVo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemList", itemList);
		modelAndView.setViewName("items/editItemsListQuery");
		return modelAndView;
	}
	
	//批量修改的提交(List参数绑定)
	@RequestMapping("/editItemsListAllSubmit")
	public String editItemsListAllSubmit(ItemsQueryVo itemsQueryVo){
		System.out.println("--------------List参数绑定--------------");
		System.out.println(itemsQueryVo.getItemsList().toString());
		return "forward:queryItems5.action";
	}
	
	//批量修改商品的页面(Map参数绑定)
	@RequestMapping("/editItemsMapQuery")
	public ModelAndView editItemsMapQuery(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception{
		List<ItemsCustomer> itemList = itemsService.queryItemList(itemsQueryVo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemList", itemList);
		modelAndView.setViewName("items/editItemsMapQuery");
		return modelAndView;
	}
	
	//批量修改的提交(Map参数绑定)
	@RequestMapping("/editItemsMapAllSubmit")
	public String editItemsMapAllSubmit(ItemsQueryVo itemsQueryVo){
		System.out.println("--------------map参数绑定--------------");
		System.out.println(itemsQueryVo.getItemsInfo().toString());
		return "forward:queryItems5.action";
	}
	
}
