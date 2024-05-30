package hello.itemservice.web.basic;



import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
