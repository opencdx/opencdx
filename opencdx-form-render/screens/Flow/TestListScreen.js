import React, { useState } from 'react';
import { View, Text, FlatList, TextInput, TouchableOpacity } from 'react-native';
import { SafeAreaView } from 'react-native';
import { Platform } from 'react-native';

import { Ionicons , Entypo, AntDesign} from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';


const TestListScreen = () => {
    const navigation = useNavigation();
    const [searchText, setSearchText] = useState('');
    const [testList, setTestList] = useState([
        { id: 1, name: 'COVID-19 Antigen Home Test' , description: 'This is an Antigen Home test for COVID-19' , price: 45},
        { id: 2, name: 'COVID-19 Nasal Swab Test' , description: 'This is a nasal swab test for COVID-19' , price: 38},
        { id: 3  , name: 'HEP Antibody Test' , description: 'This is an antibody test for HEP' , price: 50},
        { id: 4  , name: 'HEP Antigen Test' , description: 'This is an antigen test for HEP' , price: 60},
        { id: 5  , name: 'HELIX Genetic Test' , description: 'This is a genetic test for HELIX' , price: 100},
        { id: 6  , name: 'HELIX Covid-19 Test' , description: 'This is a COVID-19 test for HELIX' , price: 150},
        { id: 7  , name: 'ACCON FLOWFLEX Test' , description: 'This is a FLOWFLEX test for ACCON' , price: 200},
        
        // Add more test items here
    ]);

    const handleSearch = (text) => {
        setSearchText(text);
        const filteredList = testList.filter((test) =>
            test.name.toLowerCase().includes(text.toLowerCase())
        );
        setTestList(filteredList);
    };

    const handleBack = () => {
        navigation.goBack();
    };

    return (
        <View style={styles.container}>
            <SafeAreaView>
                <View style={styles.searchContainer}>
                    <TouchableOpacity onPress={handleBack}>
                        <Ionicons name="arrow-back" size={24} color="black" />
                    </TouchableOpacity>
                    <TextInput
                        style={styles.searchInput}
                        placeholder="Search for Test"
                        value={searchText}
                        onChangeText={handleSearch}
                    />
                </View>
                <View style={styles.popularTest}>
                <Entypo name="lab-flask" size={24} color="green" />
                                <Text style={styles.popularText}>

                    Popular Tests
                </Text>
                </View>
                <FlatList
                    data={testList}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style={styles.sectionWrapper}>
                            <View style={styles.leftSection}>
                            <Text style={{fontWeight: 'bold'}}
                            >{item.name}</Text>
                            <Text style={{color: 'gray', fontSize: 12}}
                            >{item.description}</Text>
                            </View>
                            <View style={styles.rightSection}>
                            <Text>${item.price}</Text>
                            <TouchableOpacity  style={{marginLeft: 10, paddingTop: 2}}
                            onPress={() => navigation.navigate('TestDetails', { test: item })}>
                            <AntDesign name="right" size={16} color="green" />
                            </TouchableOpacity>
                            </View>
                        </View>
                    )}
                />
            </SafeAreaView>
        </View>
    );
};

const styles = {
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        padding: 10,
        ...Platform.select({
            web: {
                maxWidth: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            }
        })
    },
    popularTest: {
        backgroundColor: '#ebf0f5',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'left',
        paddingLeft: 10,
        paddingVertical: 15,
    },
    popularText: {
        fontSize: 15,
        fontWeight: 'bold',
    },
    sectionWrapper: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#f0f0f0',
        paddingVertical: 5,


    },
    leftSection: {
        padding: 10,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',


    },
    rightSection: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        

      
    },
    searchContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 10,

    },
    searchInput: {
        flex: 1,
        marginLeft: 10,
        height: 40,
        borderColor: 'white',
        borderWidth: 1,
        borderRadius: 5,
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 10,
        paddingHorizontal: 10,
    },
};

export default TestListScreen;
