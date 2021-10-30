package com.dotori.golababdiscord;

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
public class GolababDiscordApplication {
	public static void main(String[] args) {
		SpringApplication.run(GolababDiscordApplication.class, args);
	}
}