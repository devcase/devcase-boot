package br.com.devcase.boot.webcrud;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.groups.Default;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.crud.validation.groups.Create;
import br.com.devcase.boot.crud.validation.groups.Update;
import br.com.devcase.boot.web.exceptions.EntityNotFoundException;
import br.com.devcase.boot.webcrud.criteria.CriteriaSource;

public abstract class CrudController<E, ID extends Serializable> {

	private final CriteriaRepository<E, ID> repository;
	private final String viewNamePrefix;
	private final Class<E> entityClass;
	
	public CrudController(Class<E> entityClass, CriteriaRepository<E, ID> repository, String viewNamePrefix) {
		super();
		this.repository = repository;
		Assert.isTrue(!StringUtils.startsWithIgnoreCase(viewNamePrefix, "/") && !StringUtils.startsWithIgnoreCase(viewNamePrefix, "/"), "viewNamePrefix inválido (começa com barra, termina sem barra)");
		this.viewNamePrefix = viewNamePrefix;
		this.entityClass = entityClass;
	}
	
	protected final CriteriaRepository<E, ID> repository() {
		return repository;
	}
	protected final Class<E> domainClass() {
		return entityClass;
	}
	
	protected final String viewNamePrefix() {
		return viewNamePrefix;
	}
	
	protected final String buildViewName(String suffix) {
		return viewNamePrefix.concat("/").concat(suffix);
	}

	@GetMapping({ "/", "", "/list" })
	public String list(CriteriaSource criteriaSource, Pageable pageable, Model model) {
		Page<E> page = repository.findAll(criteriaSource.getCriteria(entityClass), pageable);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("pageable", pageable);
		model.addAttribute("pageNumber", pageable.getPageNumber());
		model.addAttribute("pageSize", pageable.getPageSize());
		model.addAttribute("count", repository.count());
		model.addAttribute("pathPrefix", viewNamePrefix);

		return viewNamePrefix + "/list";
	}

	@GetMapping("/{id}")
	public String details(@PathVariable(name="id") ID id, Model model) {
		Optional<E> entity = repository.findById(id);
		if(!entity.isPresent()) {
			throw new EntityNotFoundException();
		}
		model.addAttribute("entity", entity.get());
		model.addAttribute("pathPrefix", viewNamePrefix);
		loadDetailsData(entity.get(), model);
		return viewNamePrefix + "/details";
	}
	
	@GetMapping("/{id}.json")
	@ResponseBody
	public ResponseEntity<E> getJson(@PathVariable(name="id") ID id, Model model) {
		return new ResponseEntity<E>(repository.findById(id).get(), HttpStatus.OK);
	}
	
	@GetMapping({"list.json", "json"})
	@ResponseBody
	public ResponseEntity<Page<E>> getJsonList(CriteriaSource criteriaSource, Pageable pageable, Model model) {
		return new ResponseEntity<>(repository.findAll(criteriaSource.getCriteria(domainClass()), pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name="id") ID id, Model model) {
		E entity = repository.findById(id).get();
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
		repository.deleteById(id);
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
	public String create(@Validated({ Create.class, Default.class } ) @ModelAttribute("entity") E entity,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("entity", entity);
			model.addAttribute("pathPrefix", viewNamePrefix);
			model.addAttribute("bindingResult", bindingResult);
			loadFormData(entity, model);
			return viewNamePrefix + "/form";
		}
		entity = repository.save(entity);
		return successfulCreateRedirect(repository.extractIdentifier(entity));
	}
	
	protected String successfulCreateRedirect(ID id) {
		return "redirect:/" + viewNamePrefix;
	}

	@PostMapping("/{id}")
	public String update(@PathVariable ID id,
			@Validated({Update.class, Default.class}) @ModelAttribute("entity") E entity,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("entity", entity);
			model.addAttribute("pathPrefix", viewNamePrefix);
			model.addAttribute("bindingResult", bindingResult);
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
	
	/**
	 * Sobrescrever para adicionar ao model dados necessários para montar a
	 * tela de detalhes
	 * 
	 * @param model
	 */
	protected void loadDetailsData(E entity, Model model) {

	}
}
