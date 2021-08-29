package com.stanlong.l_web_annotation;

import org.springframework.stereotype.Repository;

@Repository("studentDaoId")
public class StudentDaoImpl implements StudentDao {

	@Override
	public void addStudent() {
		System.out.println("l_web_annotation addStudent()");
	}

}
