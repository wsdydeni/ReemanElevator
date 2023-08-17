package reeman.elevator.demo.model.resources;

import java.io.Serializable;
import java.util.List;

public class Groups implements Serializable {

    private String id;
    private String name;
    private String desc;
    private List<String> products;
    private List<Kens> kens;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
    public List<String> getProducts() {
        return products;
    }

    public void setKens(List<Kens> kens) {
        this.kens = kens;
    }
    public List<Kens> getKens() {
        return kens;
    }

}
