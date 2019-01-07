package com.rhyno.rx.ch3.file;

import java.util.UUID;

public class FileService {
    public void save(UUID uuid, Rating rating) {
        System.out.println(uuid + "is" + rating);
    }
}
