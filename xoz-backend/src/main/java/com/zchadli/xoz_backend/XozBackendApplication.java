package com.zchadli.xoz_backend;

import com.zchadli.xoz_backend.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class XozBackendApplication implements CommandLineRunner {
	private final PlayerService playerService;

	public static void main(String[] args) {
		SpringApplication.run(XozBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		playerService.saveDefaultPlayer();
	}
}
