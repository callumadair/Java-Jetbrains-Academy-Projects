package contacts;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/** The type Individual record. */
class IndividualRecord extends Record {
  /** The First name. */
  private String firstName,
      /** The Surname. */
      surname,
      /** The Gender. */
      gender;

  /** The Birth date. */
  private LocalDate birthDate;

  /** Instantiates a new Individual record. */
  IndividualRecord() {
        super();
    }

  /**
   * Sets surname.
   *
   * @param surname the surname
   */
  public void setSurname(String surname) {
        updateEditTime();
        this.surname = surname;
        setName();
    }

  /**
   * Gets surname.
   *
   * @return the surname
   */
  public String getSurname() {
        return surname;
    }

  /** Sets name. */
  public void setName() {
        if (firstName != null && surname != null) {
            setName(firstName + " " + surname);
        } else if (firstName == null && surname != null) {
            setName(surname);
        } else setName(Objects.requireNonNullElse(firstName, "[no name]"));
    }

  /**
   * Sets first name.
   *
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
        return firstName;
    }

  /**
   * Sets gender.
   *
   * @param gender the gender
   */
  public void setGender(String gender) {
        updateEditTime();
        try {
            if (isValidGender(gender)) {
                this.gender = gender;
            } else {
                this.gender = "[no data]";
                throw new IllegalArgumentException("Bad gender!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

  /**
   * Is valid gender boolean.
   *
   * @param gender the gender
   * @return the boolean
   */
  private boolean isValidGender(String gender) {
        return gender.matches("[MF]");
    }

  /**
   * Gets gender.
   *
   * @return the gender
   */
  public String getGender() {
        return gender;
    }

  /**
   * Sets birth date.
   *
   * @param birthDate the birth date
   */
  public void setBirthDate(String birthDate) {
        updateEditTime();
        try {
            if (!birthDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                throw new DateTimeException("Bad birth date!");
            } else {
                this.birthDate = LocalDate.parse(birthDate);
            }
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
        }

    }

  /**
   * Gets birth date.
   *
   * @return the birth date
   */
  private LocalDate getBirthDate() {
        return birthDate;
    }

  /**
   * Gets birth date string.
   *
   * @return the birth date string
   */
  public String getBirthDateString() {
        if (this.getBirthDate() != null) {
            return this.getBirthDate().toString();
        } else {
            return "[no data]";
        }

    }

    @Override
    public String toString() {
        return "Name: " + getFirstName() + "\n" +
                "Surname: " + getSurname() + "\n" +
                "Birth date: " + getBirthDateString() + "\n" +
                "Gender: " + getGender() + "\n" +
                "Number: " + getNumber() + "\n" +
                super.toString();
    }

    @Override
    public String[] getFields() {
        return new String[]{Fields.NAME.label, Fields.SURNAME.label,
                Fields.GENDER.label, Fields.BIRTH.label, Fields.NUMBER.label};
    }

    @Override
    public void setField(String field, String value) {
        Fields fields = Fields.valueOf(field.toUpperCase());
        switch (fields) {
            case NAME:
                setFirstName(value);
                break;
            case SURNAME:
                setSurname(value);
                break;
            case GENDER:
                setGender(value);
                break;
            case BIRTH:
                setBirthDate(value);
                break;
            case NUMBER:
                setNumber(value);
                break;
            default:
                System.out.println("Error!");
                break;
        }
    }

    @Override
    public String getFieldValue(String field) {
        Fields fields = Fields.valueOf(field.toUpperCase());
        switch (fields) {
            case NAME:
                return getFirstName();
            case SURNAME:
                return getSurname();
            case BIRTH:
                return getBirthDateString();
            case GENDER:
                return getGender();
            case NUMBER:
                return getNumber();
            case CREATION:
                return super.getCreationDateTime().truncatedTo(ChronoUnit.MINUTES).toString();
            case EDIT:
                return super.getEditDateTime().truncatedTo(ChronoUnit.MINUTES).toString();
            default:
                return null;
        }
    }
}