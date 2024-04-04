package ua.dokat.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SkinBuyingRepository {

    private final List<String> lastSkinsFounds = new ArrayList<>();

    public void add(String skinId){
        if (!lastSkinsFounds.isEmpty()) lastSkinsFounds.clear();
        lastSkinsFounds.add(skinId);
    }

    public String getAndClear(){
        String skinId = lastSkinsFounds.get(0);
        lastSkinsFounds.clear();
        return skinId;
    }

    public boolean isEmpty(){
        return lastSkinsFounds.isEmpty();
    }
}