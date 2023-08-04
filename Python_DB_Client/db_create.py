import mysql.connector
from faker import Faker
from datetime import datetime
import os

def create_database(host, user, password, database_name):
    try:
        connection = mysql.connector.connect(
            host=host,
            user=user,
            password=password
        )

        cursor = connection.cursor()
        cursor.execute(f"CREATE DATABASE IF NOT EXISTS {database_name}")

        connection.close()
        print(f"Database '{database_name}' created successfully.")
    except mysql.connector.Error as error:
        print(f"Error creating database: {error}")

def create_table(host, user, password, database_name):
    try:
        connection = mysql.connector.connect(
            host=host,
            user=user,
            password=password,
            database=database_name
        )

        cursor = connection.cursor()
        cursor.execute("SHOW TABLES LIKE 'person'")
        result = cursor.fetchone()
        if result:
            print("Table 'person' already exists.")
        else:
            cursor.execute("""
                CREATE TABLE person (
                    phone_number VARCHAR(20) PRIMARY KEY,
                    first_name VARCHAR(100) NOT NULL,
                    last_name VARCHAR(100) NOT NULL
                )
            """)
            print("Table 'person' created successfully.")

        connection.close()

    except mysql.connector.Error as error:
        print(f"Error creating table: {error}")

def generate_fake_data(num_entries, max_phone_number_length=20):
    fake = Faker()
    data = []
    for _ in range(num_entries):
        name = fake.first_name()
        surname = fake.last_name()
        phone_number = fake.numerify('##########')   # 10 digits
        #last_modified = datetime.now()
        data.append((phone_number, name, surname))

    print(f"Generated {len(data)} entries.")
    return data


def insert_data(host, user, password, database_name, data):
    try:
        connection = mysql.connector.connect(
            host=host,
            user=user,
            password=password,
            database=database_name
        )

        cursor = connection.cursor()

        sql = "INSERT INTO person (phone_number, first_name, last_name) VALUES (%s, %s, %s)"
        cursor.executemany(sql, data)

        connection.commit()
        connection.close()
        print(f"{len(data)} entries inserted into the 'person' table.")
    except mysql.connector.Error as error:
        print(f"Error inserting data: {error}")

if __name__ == "__main__":
    HOST = "localhost"
    USER = "root"
    PASSWORD = os.environ.get('DB_PASSWORD')
    DATABASE_NAME = "AddressBook"
    NUM_ENTRIES = 250

    create_database(HOST, USER, PASSWORD, DATABASE_NAME)
    create_table(HOST, USER, PASSWORD, DATABASE_NAME)

    fake_data = generate_fake_data(NUM_ENTRIES, max_phone_number_length=20)
    insert_data(HOST, USER, PASSWORD, DATABASE_NAME, fake_data)
