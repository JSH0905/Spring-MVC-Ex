package hello.itemservice.domain.item;


import lombok.Getter;
import lombok.Setter;

//@Data

@Getter @Setter  // @Data는 예상치  못하게 동작할 수 있는 등 굉장히 위험한 방법이 될수도있다. 만약 쓰게된다면 잘 확인하고 사용할 것.
public class Item {

    /**
     * Integer 쓴 이유 : price, quantity에 값이 안들어갈 수도 있어서. = null이 갈수도 있다.
     */
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
