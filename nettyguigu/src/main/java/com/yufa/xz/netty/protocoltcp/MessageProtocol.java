package com.yufa.xz.netty.protocoltcp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @data 2020/9/4
 */

@Getter
@Setter
public class MessageProtocol {
    private int len;
    private byte[] content;


}
