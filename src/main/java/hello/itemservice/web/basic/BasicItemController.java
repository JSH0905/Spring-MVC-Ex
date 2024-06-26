package hello.itemservice.web.basic;



import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


/**
 * @RequiredArgsConstructor -> final 붙은 애들의 생성자를 자동으로 만들어줌.
 * itemRepository 경우 따로 생성자 직접 주입할 필요 없음.
 * 생성자 하나일때는 @AutoWired 생략 가능.
 */

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";

    }

    @GetMapping("/add")
    public String addFrom(){
        return "/basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);


        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){

        itemRepository.save(item);
//        model.addAttribute("item", item); // 자동 추가, 생략 가능

        return "/basic/item";
    }


//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){

// 아무것도 안넣으면 모델 이름이 Item -> item으로 인식됨.
        itemRepository.save(item);
//        model.addAttribute("item", item); // 자동 추가, 생략 가능

        return "/basic/item";
    }

    /**
     * 우리가 만든 임의의 객체에 대해서는 @ModelAttribute 생략 가능.
     * 수정 전 addItemV4의 치명적 문제점 : 상품을 추가하고 반환받은 뷰에서 새로고침하면 사용자가 마지막으로 행한 Post/add가 다시 실행되어
     * 사용자의 의도와 다르게 추가했던 상품이 또 추가되는 현상이 발생한다.
     * 해결방법 : PRG(Post/Redirect/Get) 패턴을 사용하여 사용자가 마지막으로 행한 행위를 Get요청으로 변경하여 무한으로 상품이 추가되는 것을 막는다.
     */
//    @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true); //쿼리파라미터로 들어감.
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){

        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }




    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
