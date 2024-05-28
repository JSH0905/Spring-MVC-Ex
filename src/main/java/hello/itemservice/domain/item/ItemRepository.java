package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  실제는 hashmap 쓰면 안됨. 쓴다면 ConcurrentHashMap 사용해야함.
 *  id값의 long 타입도 실제로는 Atomic long을 사용해야함.
 *
 *  update 메서드에서 만약 프로젝트 규모가 더 커진다면, DTO를 따로 만드는것이 더 명확해서 좋음.
 */
@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;


    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clear(){
        store.clear();
    }
}
