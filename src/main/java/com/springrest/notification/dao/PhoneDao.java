package com.springrest.notification.dao;

import com.springrest.notification.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

//redis repository
@Repository
public class PhoneDao {
    public static final String HASH_KEY="Phone";
    @Autowired
    private RedisTemplate template;
    public boolean save(Phone phone)
    {
        try{
            template.opsForHash().put(HASH_KEY,phone.getPhone_number(),phone);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
    public boolean delete(Phone phone)
    {
        try{
            template.delete(phone);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public List<Phone> findAll()
    {
        List<Phone> blocked_numbers=template.opsForHash().values(HASH_KEY);;
        return blocked_numbers;
    }
    //public String deletePhone()
}
