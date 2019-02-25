package me.javagyb.data.supplier.test;

import lombok.Data;
import me.javagyb.data.supplier.annotations.DS_RandomString;
import me.javagyb.data.supplier.annotations.DS_StringRange;

/**
 * Created by javagyb   on 2018/1/29.
 */
@Data
public class Addr {
    @DS_RandomString(tuple = {"sss","222","3333","444"})
    private String add;
}
