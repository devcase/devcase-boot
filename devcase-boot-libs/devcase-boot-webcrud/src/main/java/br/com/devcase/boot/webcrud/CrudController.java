package br.com.devcase.boot.webcrud;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.devcase.boot.crud.validation.groups.Create;
import br.com.devcase.boot.crud.validation.groups.Update;

public abstract class CrudController<E, ID extends Serializable> {

	private final JpaRepository<E, ID> repository;
	private final String viewNamePrefix;
	private final Class<E> entityClass;

	public CrudController(Class<E> entityClass, JpaRepository<E, ID> repository, String viewNamePrefix) {
		super();
		this.repository = repository;
		Assert.isTrue(!StringUtils.startsWithIgnoreCase(viewNamePrefix, "/") && !StringUtils.startsWithIgnoreCase(viewNamePrefix, "/"), "viewNamePrefix inválido (começa com barra, termina sem barra)");
		this.viewNamePrefix = viewNamePrefix;
		this.entityClass = entityClass;
	}

	@GetMapping({ "/", "", "/list" })
	public String list(Pageable pageable, Model model) {
		Page<E> page = repository.findAll(pageable);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("pageNumber", pageable.getPageNumber());
		model.addAttribute("pageSize", pageable.getPageSize());
		model.addAttribute("count", repository.count());
		model.addAttribute("pathPrefix", viewNamePrefix);

		return viewNamePrefix + "/list";
	}

	@GetMapping("/{id}")
	public String details(@PathVariable ID id, Model model) {
		model.addAttribute("entity", repository.findOne(id));
		model.addAttribute("pathPrefix", viewNamePrefix);
		return viewNamePrefix + "/details";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable ID id, Model model) {
		E entity = repository.findOne(id);
		model.addAttribute("entity", entity);
		model.addAttribute("pathPrefix", viewNamePrefix);
		loadFormData(entity, model);
		return viewNamePrefix + "/form";
	}

	@GetMapping("/create")
	public String create(Model model) {
		E entity = createInstance(model);
		model.addAttribute("entity", entity);
		model.addAttribute("pathPrefix", viewNamePrefix);
		loadFormData(entity, model);
		return viewNamePrefix + "/form";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable ID id, Model model) {
		repository.delete(id);
		return "redirect:/" + viewNamePrefix;
	}
	
	protected E createInstance(Model model) {
		try {
			return entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}

	@PostMapping({"", "/"})
	public String create(@Validated(Create.class) @ModelAttribute("entity") E entity,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("entity", entity);
			model.addAttribute("pathPrefix", viewNamePrefix);
			loadFormData(entity, model);
			return viewNamePrefix + "/form";
		}
		entity = repository.save(entity);
		return "redirect:/" + viewNamePrefix;
	}

	@PostMapping("/{id}")
	public String update(@PathVariable ID id,
			@Validated(Update.class) @ModelAttribute("entity") E entity,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("entity", entity);
			model.addAttribute("pathPrefix", viewNamePrefix);
			model.addAttribute("results", bindingResult);
			loadFormData(entity, model);
			return viewNamePrefix + "/form";
		}
		entity = repository.save(entity);
		return "redirect:/" + viewNamePrefix + "/" + id;
	}

	/**
	 * Sobrescrever para adicionar ao model dados necessários para montar o
	 * formulário (exemplo: lista para <select>)
	 * 
	 * @param model
	 */
	protected void loadFormData(E entity, Model model) {

	}
}
