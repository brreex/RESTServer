package cs544.exercise25_1.service;

import cs544.exercise25_1.generated.shoppingList.Item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ShoppingListController {

	private IShoppingListService shoppingListService;
	
	public void setShoppingListService(IShoppingListService shoppingListService) {
		this.shoppingListService = shoppingListService;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("list", shoppingListService.getList());
		return "marshalview";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String addItem(@RequestBody Item item) {
		shoppingListService.addToList(item);
		return "redirect:/list";
	}

	@RequestMapping(value = "/item/{product}*", method = RequestMethod.GET)
	public String item(@PathVariable("product") String product,Model model) {
		Item item = shoppingListService.getItem(product);
		if (item != null) {
			model.addAttribute("item", item);
		}
		return "item";
	}

	@RequestMapping(value = "/item/{product}", method = RequestMethod.PUT)
	public String updateItem(@RequestBody Item item) {

		shoppingListService.updateItem(item);
		return "redirect:/"+(item.getProduct());
	}

	@RequestMapping(value = "/item/{product}", method = RequestMethod.DELETE)
	public String deleteItem(@PathVariable("product") String product) {
		shoppingListService.removeFromList(product);
		return "redirect:/list";
	}

}
