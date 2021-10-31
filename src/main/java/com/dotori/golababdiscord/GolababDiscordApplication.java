package com.dotori.golababdiscord;

import io.github.key_del_jeeinho.cacophony_lib.autoconfigure.UseCacophony;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
SPDX-FileCopyrightText: © 2021 JeeInho <velocia.developer@gmail.com>
SPDX-License-Identifier: CC BY-NC-ND
 */
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
@UseCacophony
public class GolababDiscordApplication {
	public static void main(String[] args) {
		SpringApplication.run(GolababDiscordApplication.class, args);
	}
}