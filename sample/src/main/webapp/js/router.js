Todos.Router.map(function() {
    this.resource('todos', {
        path: '/'
    }, function() {
        this.route('active');
        this.route('completed');
    });
});

Todos.TodosCompletedRoute = Ember.Route.extend({
    model: function() {
        return this.store.filter('todo', function(todo) {
            return todo.get('isCompleted');
        });
    },
    renderTemplate: function(controller) {
        this.render('todos/index', {
            controller: controller
        });
    }
});

Todos.TodosActiveRoute = Ember.Route.extend({
    model: function(){
        return this.store.filter('todo', function(todo) {
            return !todo.get('isCompleted');
        });
    },
    renderTemplate: function(controller) {
        this.render('todos/index', {
            controller: controller
        });
    }
});

Todos.TodosIndexRoute = Ember.Route.extend({
    model: function() {
        return this.modelFor('todos');
    }
});

Todos.TodosRoute = Ember.Route.extend({
    model: function() {
        return this.store.find('todo');
    }
});

