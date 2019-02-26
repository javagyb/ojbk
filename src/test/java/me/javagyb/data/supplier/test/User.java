package me.javagyb.data.supplier.test;

import lombok.Data;
import me.javagyb.data.supplier.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by javagyb on 2018/1/29.
 */
@Data
@DS_Override(true)
public class User {


    private String name;
    @DS_RandomInteger
    private Integer age=1;

    @DS_RandomInteger
    private Long age2;

    @DS_Regx("[0-9a-z]{32}")
    @DS_Size(1)
    private List<String> ss;

    @DS_Size(1)
    @DS_RandomString(tuple = {"1","2","3"})
    private String[] te;

    private BigDecimal ds;

    @DS_RandomInteger
    private int[] s;

    private Date start;

    private String email;

    private Type type;
//    private Addr[] addr;

}
