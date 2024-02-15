package fr.ancyracademy.esportsclash.auth.application.exceptions;

import fr.ancyracademy.esportsclash.core.domain.exceptions.BadRequestException;

public class EmailAddressUnavailableException extends BadRequestException {
  public EmailAddressUnavailableException() {
    super("Email address is already in use");
  }
}
