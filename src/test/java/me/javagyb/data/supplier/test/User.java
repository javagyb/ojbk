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

    @DS_Regx("[0-9a-z]{32}")
    private String name;
    @DS_RandomInteger
    private Integer age=1;
    private Long age2;

    @DS_Regx("[0-9a-z]{32}")
    @DS_Size(1)
    private List<String> ss;

    private String[] te;

    private BigDecimal ds;

    @DS_RandomInteger
    private int[] s;

    @DS_Date("2011-2-22 00:00:00")
    private Date start;

    @DS_Regx("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")
    private String email;

//    private Addr[] addr;

}
