import requests
from datetime import datetime

base_url = "http://localhost:8081/contacts"

"""
POST
"""
def add_new_person(person):
    url = base_url + "/add"
    response = requests.post(url, json=person)

    print("Response Status Code:", response.status_code)
    print("Response Text:", response.text)

    if response.status_code == 201:
        return response.text
    else:
        return "CLIENT - Error adding the person. Status Code: " + str(response.status_code)


"""
DELETE
"""
def remove_person_by_phone_number(phone_number):
    url = base_url + "/delete/" + phone_number
    response = requests.delete(url)

    print("Response Status Code:", response.status_code)
    print("Response Text:", response.text)

    if response.status_code == 200 or response.status_code == 204:
        return response.text
    elif response.status_code == 404:
        return "CLIENT - Person not found with phone number: " + phone_number
    else:
        return "CLIENT - Error deleting the person. Status Code: " + str(response.status_code)


"""
GET
"""
def get_all_persons():
    url = base_url + "/retrieveAll"
    response = requests.get(url)

    print("Response Status Code:", response.status_code)
    print("Response Text:", response.text)

    if response.status_code == 200:
        try:
            return response.json()
        except Exception as e:
            print("Error parsing JSON:", e)
            return "Error parsing JSON"
    else:
        return "CLIENT - Error retrieving all persons. Status Code: " + str(response.status_code)
    
def search_persons_by_surname(surname):
    url = base_url + "/searchByLastName/" + surname
    response = requests.get(url)

    print("Response Status Code:", response.status_code)
    print("Response Text:", response.text)

    if response.status_code == 200:
        try:
            return response.json()
        except Exception as e:
            print("Error parsing JSON:", e)
            return "Error parsing JSON"
    else:
        return "CLIENT - Error searching persons by surname. Status Code: " + str(response.status_code)

def search_persons_by_phone_number(phone_number):
    url = base_url + "/searchByPhoneNumber/" + phone_number
    response = requests.get(url)

    print("Response Status Code:", response.status_code)
    print("Response Text:", response.text)

    if response.status_code == 200:
        try:
            return response.json()
        except Exception as e:
            print("Error parsing JSON:", e)
            return "Error parsing JSON"
    else:
        return "CLIENT - Error searching persons by phone number. Status Code: " + str(response.status_code)

"""
PUT
"""
def update_person(phone_number, person):
    url = base_url + "/update/" + phone_number

    response = requests.put(url, json=person)

    print("Response Status Code:", response.status_code)
    print("Response Text:", response.text)

    if response.status_code == 200 or response.status_code == 204:
        return response.text
    elif response.status_code == 404:
        return "CLIENT - Person not found."
    else:
        return "CLIENT - Error updating the person. Status Code: " + str(response.status_code)


if __name__ == '__main__':
   
    ## POST - working
    print("1) ADDING ")
    p1 = {"firstName":"giovanni","lastName":"rossi","phoneNumber":"0099970193"}
    p11 = {"firstName":"aldo","lastName":"rossi","phoneNumber":"0088970193"}
    p111 = {"firstName":"carlo","lastName":"rossi","phoneNumber":"9999970193"}
    p1111 = {"firstName":"franco","lastName":"rossi","phoneNumber":"1199070193"}

    print(add_new_person(p1))
    print(add_new_person(p11))
    print(add_new_person(p111))
    print(add_new_person(p1111))

    ## GET - working
    print("\n2.1) SEARCH BY SURNAME ")
    #print(len(get_all_persons()))
    print(len(search_persons_by_surname('rossi'))) # = 4
    print("\n2.2) SEARCH BY PHONE ")
    print(search_persons_by_phone_number('0099970193'))

    ## PUT - working
    print("\n3) UPDATE - update name and surname ")
    p2 = {"firstName":"giovannino","lastName":"bianchi","phoneNumber":"0099970193"}
    print(update_person("0099970193", p2))
    # not possible to update the ID 
    # it is needed to delete person with old ID and recreate it with new ID

    ## REMOVE - working
    print("\n4) REMOVE BY PHONE ")
    print(remove_person_by_phone_number('0099970193'))

    # IF DECOMMENTED THIS WILL RAISE A 404 ERROR (IT IS CORRECT,NUMBER IS REMOVED)
    #print(search_persons_by_phone_number('0099970193'))




