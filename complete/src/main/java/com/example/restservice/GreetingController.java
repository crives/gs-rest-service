package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	@Autowired
	private CounterRepository counterRepository;
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

//	@GetMapping("/persistent_greeting")
//	public Greeting persistentGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//		Counter persistentCounter = counterRepository.findById(1L).orElseGet(() -> new Counter(0L));
//		Long persistentCounterValue = persistentCounter.incrementAndGet();
//		counterRepository.save(persistentCounter);
//		return new Greeting(persistentCounterValue, String.format(template, name));
//	}


	@GetMapping("/persistent_greeting")
	public Greeting persistentGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		Counter persistentCounter = counterRepository.findById("spring_counter").orElseGet(() ->
				new Counter(0L, "spring_counter"));
		Long persistentCounterValue = persistentCounter.incrementAndGet();
		counterRepository.save(persistentCounter);
		return new Greeting(persistentCounterValue, String.format(template, name));
	}

//	@GetMapping("/set_count")
//	public Greeting setCount(@RequestParam(value = "value", defaultValue = "0") Long value) {
//		Counter newCounter = counterRepository.findById(1L).orElseGet(() -> new Counter(0L));
//		newCounter.setCounter(value);
//		counterRepository.save(newCounter);
//		return new Greeting(newCounter.getCounter(), "Count value set.");
//	}
//
//	@GetMapping("/get_count")
//	public Greeting getCount() {
//		Counter newCounter = counterRepository.findById(1L).orElseGet(() -> new Counter(0L));
//		return new Greeting(newCounter.getCounter(), "Count value get.");
//	}
//
//	@GetMapping("/set_count_trace")
//	public Greeting setCountTrace(@RequestParam(value = "value", defaultValue = "0") Long value) {
//		Counter newCounter = counterRepository.findById(1L).orElseGet(() -> new Counter(0L));
//		String counterRepositoryOutput = counterRepository.setCounterAndTrace(value);
//		return new Greeting(Long.parseLong(counterRepositoryOutput.split(",")[1], 10),
//				String.format("Count value set, on instance %s",
//						counterRepositoryOutput.split(",")[2]));
//	}
//
//	@GetMapping("/get_count_trace")
//	public Greeting getCountTrace() {
//		String counterRepositoryOutput = counterRepository.getCounterAndTrace();
//		return new Greeting(Long.parseLong(counterRepositoryOutput.split(",")[1], 10),
//				String.format("Count value get, on instance %s",
//						counterRepositoryOutput.split(",")[2]));
//	}

}
