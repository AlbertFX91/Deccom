package com.deccom.service.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.deccom.domain.core.extractor.ControlVariableExtractor;
import com.deccom.domain.core.extractor.ControlVariableExtractorImpl;
import com.deccom.domain.core.extractor.Item_ControlVariableExtractor;

@Service
public class ControlVariableExtractorService {

	private final Logger log = LoggerFactory.getLogger(ControlVariableExtractorService.class);
	private static final String i18nCodeRoot = "operations.controlvariableextractor";

	public Item_ControlVariableExtractor create(ControlVariableExtractor c) {
		return new Item_ControlVariableExtractor(c.getClass().getName(), c.getStyle(), c.getUid());
	}

	public List<Item_ControlVariableExtractor> getAllListExtractors() {
		List<Item_ControlVariableExtractor> res = new ArrayList<>();
		List<ControlVariableExtractor> aux = getAllExtractors();
		for (ControlVariableExtractor c : aux) {
			res.add(create(c));
		}
		return res;
	}

	public List<ControlVariableExtractor> getAllExtractors() {
		List<ControlVariableExtractor> res = new ArrayList<>();
		Set<Class<? extends ControlVariableExtractor>> subTypes = getSubClassesOf(ControlVariableExtractor.class);

		for (Class<? extends ControlVariableExtractor> c : subTypes) {
			try {
				ControlVariableExtractor e = c.newInstance();
				// Removing the sample controlVariableExtractor
				if (!e.getClass().equals(ControlVariableExtractorImpl.class)) {
					res.add(e);
				}
			} catch (InstantiationException | IllegalAccessException e) {

			}

		}
		return res;
	}

	public ControlVariableExtractor findOne(String id) {
		Optional<ControlVariableExtractor> res = getAllExtractors().stream().filter(x -> x.getUid().equals(id))
				.findFirst();
		return res.isPresent() ? res.get() : null;
	}

	@SuppressWarnings("unchecked")
	private static <T> Set<Class<? extends T>> getSubClassesOf(Class<T> cls) {
		Reflections reflections = new Reflections(
				new ConfigurationBuilder().setUrls(Arrays.asList(ClasspathHelper.forClass(cls))));
		Set<?> subTypes = reflections.getSubTypesOf(cls);
		return subTypes.stream().map((o) -> (Class<? extends T>) o).filter((c) -> !c.isInterface())
				.collect(Collectors.toSet());
	}

}
