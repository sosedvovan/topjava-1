package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
//бизнес логика для User. если ей нужен доступ к ДБ, то она обращается к repository

// @Service указывает на то, что в контексте Спринга будет создан Бин этого класса для дальнейшей DI этого бина
@Service
public class UserService {
    ////клас имеет в поле репозиторий InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository
    //    //и методы типа CRUD для работы с этим репозиторием

    //    поле- интерфейс MealRepository получает свою реализацию в виде InMemoryUserRepository
    //    это репозиторий к которому будет обращаться этот сервис, если ему что-то понадобится в ДБ
    private final UserRepository repository;

    //    конструктор. Спринг автоматически инициализирует поле repository бином UserRepository кот берет из своего контекста.
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

//  вызывает метод репозитория  repository.save(user)
    public User create(User user) {
        return repository.save(user);
    }

//  произойдет удаление и результат(буль) работы repository.delete(id) еще отправиться на проверку на NotFound
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

//  //  произойдет взятие и результат(User) работы repository.get(id) еще отправиться на проверку на NotFoundWithId
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

//  произойдет взятие по емейлу и результат(User) работы getByEmail(email) еще отправиться на проверку на NotFound
    public User getByEmail(String email) throws NotFoundException {
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

//  возвращает List<User> - это мы вернем возврат из метода из repository.getAll()
    public List<User> getAll() {
        return repository.getAll();
    }

//  произойдет update и результат(User) работы repository.delete(id) еще отправиться на проверку на NotFoundWithId
    public void update(User user) throws NotFoundException {
        checkNotFoundWithId(repository.save(user), user.getId());
    }
}