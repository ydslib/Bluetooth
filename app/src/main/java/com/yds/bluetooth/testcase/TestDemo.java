package com.yds.bluetooth.testcase;

import android.bluetooth.BluetoothAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by yds
 * on 2020/4/10.
 */
public class TestDemo {
    private static BluetoothAdapter sBluetoothAdapter;

    @BeforeClass
    public static void classBefore() {
        sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Before
    public void before() {

    }

    @Test
    public void getNameTest() {
        String name = sBluetoothAdapter.getName();
        Assert.assertNotNull("name should not be null", name);
    }
}
