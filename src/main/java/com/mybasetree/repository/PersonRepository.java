package com.mybasetree.repository;

import com.mybasetree.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;


//@Repository - Отвечает за взаимодействие с базой данных. Это прослойка между приложением и СУБД.
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByFirstName(String firstName);//найти по имени
    List<Person> findByLastName(String lastName); //найти по фамилии
    List<Person> findByFirstNameAndLastName(String firstName, String lastName); // поиск по имени и фамилии

    boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);

    //поиск в адресе рождения:
    //поиск в адресе рождения по губернии
    @Query("SELECT p FROM Person p JOIN p.addresses a WHERE a.addressType = com.mybasetree.entity.AddressType.BIRTH AND a.gubernia = :gubernia")
    List<Person> findByPersonsByBirthGubernia(@Param("gubernia") String gubernia);

    //поиск в адресе рождения по населенному пункту
    @Query("SELECT p FROM Person p JOIN p.addresses a WHERE a.addressType = com.mybasetree.entity.AddressType.BIRTH AND a.naseleniyPunct = :naseleniyPunct")
    List<Person> findByPersonsByBirthNaseleniyPunct(@Param("naseleniyPunct") String naseleniyPunct);

    //поиск в адресе проживания:
    //поиск в адресе проживания по губернии
    @Query("SELECT p FROM Person p JOIN p.addresses a WHERE a.addressType = com.mybasetree.entity.AddressType.LIVEONE AND a.gubernia = :gubernia")
    List<Person> findByPersonsByLiveGubernia(@Param("gubernia") String gubernia);

    //поиск в адресе проживания по населенному пункту
    @Query("SELECT p FROM Person p JOIN p.addresses a WHERE a.addressType = com.mybasetree.entity.AddressType.LIVEONE AND a.naseleniyPunct = :naseleniyPunct")
    List<Person> findByPersonByLiveNaseleniyPunct(@Param("naseleniyPunct") String naseleniyPunct);

    //поиск по любому адресу (рождения, проживания и.т.д.)
    @Query("SELECT p FROM Person p JOIN p.addresses a WHERE a.gubernia = :gubernia")
    List<Person> findByAllAddressGubernia(@Param("gubernia") String gubernia);

    @Query("SELECT p FROM Person p JOIN p.addresses a WHERE a.naseleniyPunct = :naseleniyPunct")
    List<Person> findByAllAddressNaseleniyPunct(@Param("naseleniyPunct") String naseleniyPunct);

    List<Person> findByDateOfBirthBefore(LocalDate date); //родившиеся ДО даты
    List<Person> findByDateOfBirthAfter(LocalDate date); //родившиеся ПОСЛЕ даты
}
