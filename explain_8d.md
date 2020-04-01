Pageable
ResponseEntity


    embedded[0]?
    
    eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
    
    Link..?
    
    

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler){
        Page<Event> page = this.eventRepository.findAll(pageable);
                                // assembler.toResource(...) wasted my time ...
        var pagedResources = assembler.toModel(page, e -> new EventResource(e));
        pagedResources.add(new Link("/docs/index.html#resources-events-list").withRel("profile"));
        return ResponseEntity.ok(pagedResources);

    }
    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id){
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        return optionalEvent.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok((new EventResource(optionalEvent.get())).add(new Link("/docs/index.html#resources-events-get").withRel("profile")));
    }
    
    
    @Test
    @TestDescription("Search 30 pages of 10 events twice")
    public void queryEvents() throws Exception {

        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // When
        this.mockMvc.perform(get("/api/events")
                .param("page", "1") // 0,1  so 1
                .param("size", "10")
                .param("sort", "name,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))

        ;
    }


    @Test
    @TestDescription("Searching for an existing event")
    public void getEvent() throws Exception{

        // Given
        Event event = this.generateEvent(100);

        // When & Then
        this.mockMvc.perform(get("/api/events/{id}", event.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-event"))
        ;
    }

    @Test
    @TestDescription("take 404 when it is not exist")
    public  void getEvent404() throws Exception{
        // When & Then
        this.mockMvc.perform(get("/api/events/404404"))
                .andExpect(status().isNotFound())
        ;
    }
}


