package br.org.edu.ifrn.LojaCarro.controllers;

public class CarroInvalidoException extends RuntimeException {

    public CarroInvalidoException(String message) {
        super(message);
    }
}
