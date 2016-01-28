/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kylecorry.util;

/**
 *
 * @author kyle
 */
import java.io.InputStream;

public final class ResourceLoader {

    public ResourceLoader() {
    }

    public static InputStream load(String path) {
        InputStream is = ResourceLoader.class.getResourceAsStream("/" + path);
        return is;
    }
}
