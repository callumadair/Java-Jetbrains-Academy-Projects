package contacts;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** The type Record. */
abstract class Record {
  /** The Number. */
  private String number,
      /** The Name. */
      name;

  /** The Creation date time. */
  private LocalDateTime creationDateTime,
      /** The Edit date time. */
      editDateTime;

  /** Instantiates a new Record. */
  Record() {
    setCreationDateTime(LocalDateTime.now());
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    updateEditTime();
    this.name = name;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets number.
   *
   * @param number the number
   */
  public void setNumber(String number) {
    updateEditTime();
    try {
      if (isValidPhoneNumber(number)) {
        this.number = number;
      } else {
        this.number = "[no number]";
        throw new IllegalArgumentException("Wrong number format!");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Checks if the phone number provided is a valid format
   *
   * @param phoneNumber the phone number
   * @return the boolean
   */
  private boolean isValidPhoneNumber(String phoneNumber) {
    String firstRegex = "(\\+?\\(?[A-Za-z0-9]+\\)?)([-| ][A-Za-z0-9]{2,}[-| ]?)*";
    String secondRegex = "(\\+?[A-Za-z0-9]+)([-| ]\\(?[A-Za-z0-9]{2,}\\)?)([-| ][A-Za-z0-9]{2,})*";
    Pattern pattern = Pattern.compile(firstRegex + "|" + secondRegex);
    Matcher matcher = pattern.matcher(phoneNumber);
    return matcher.matches();
  }

  /**
   * Gets number.
   *
   * @return the number
   */
  public String getNumber() {
    return number;
  }

  /**
   * Sets creation date time.
   *
   * @param creationDateTime the creation date time
   */
  private void setCreationDateTime(LocalDateTime creationDateTime) {
    this.creationDateTime = creationDateTime;
    updateEditTime();
  }

  /**
   * Gets creation date time.
   *
   * @return the creation date time
   */
  public LocalDateTime getCreationDateTime() {
    return creationDateTime;
  }

  /** Update edit time. */
  protected void updateEditTime() {
    this.editDateTime = LocalDateTime.now();
  }

  /**
   * Gets edit date time.
   *
   * @return the edit date time
   */
  public LocalDateTime getEditDateTime() {
    return editDateTime;
  }

  @Override
  public String toString() {
    return "Time created: "
        + getCreationDateTime().truncatedTo(ChronoUnit.MINUTES)
        + "\nTime last edit: "
        + getEditDateTime().truncatedTo(ChronoUnit.MINUTES);
  }

  /**
   * Sets field.
   *
   * @param field the field
   * @param value the value
   */
  public abstract void setField(String field, String value);

  /**
   * Gets field value.
   *
   * @param field the field
   * @return the field value
   */
  public abstract String getFieldValue(String field);

  /**
   * Get fields string [ ].
   *
   * @return the string [ ]
   */
  public abstract String[] getFields();
}

enum Fields {
  NUMBER("number"),
  NAME("name"),
  SURNAME("surname"),
  GENDER("gender"),
  BIRTH("birth"),
  ADDRESS("address"),
  CREATION("creationDateTime"),
  EDIT("editDateTime");

  String label;

  /**
   * Instantiates a new Fields.
   *
   * @param label the label
   */
  Fields(String label) {
    this.label = label;
  }
}
