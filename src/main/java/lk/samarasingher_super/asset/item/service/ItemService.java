package lk.samarasingher_super.asset.item.service;

import lk.samarasingher_super.asset.common_asset.model.enums.LiveOrDead;
import lk.samarasingher_super.asset.item.dao.ItemDao;
import lk.samarasingher_super.asset.item.entity.Item;
import lk.samarasingher_super.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig( cacheNames = "item" )
public class ItemService implements AbstractService<Item, Integer> {
    private final ItemDao itemDao;

    @Autowired
    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> findAll() {
        return itemDao.findAll();
    }

    public Item findById(Integer id) {
        return itemDao.getOne(id);
    }

    public Item persist(Item item) {
        if(item.getId()==null){
            item.setLiveOrDead(LiveOrDead.ACTIVE);}
        return itemDao.save(item);
    }

    public boolean delete(Integer id) {
        Item item =  itemDao.getOne(id);
        item.setLiveOrDead(LiveOrDead.STOP);
        itemDao.save(item);
        return false;
    }

    public List<Item> search(Item item) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Item> itemExample = Example.of(item, matcher);
        return itemDao.findAll(itemExample);
    }

    public Item lastItem() {
        return itemDao.findFirstByOrderByIdDesc();
    }
}
