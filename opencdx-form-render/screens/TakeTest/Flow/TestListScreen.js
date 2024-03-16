import React, { useState } from 'react';
import { View, Text, FlatList, TextInput, TouchableOpacity } from 'react-native';
import { SafeAreaView } from 'react-native';
import { Platform } from 'react-native';
import { Surface } from 'react-native-paper';
import { Ionicons , Entypo, AntDesign} from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';


const TestListScreen = () => {
    const navigation = useNavigation();
    const [searchText, setSearchText] = useState('');
    const [testList, setTestList] = useState([
        { id: 1, name: 'COVID-19 Antigen Home Test' , description: 'This is an antigen test for COVID-19' , price: 45},
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
                   
                </View>
                
                <FlatList
                    data={testList}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <TouchableOpacity style={{ marginLeft: 10, paddingTop: 2 }}
                            onPress={() => navigation.navigate('TestDetails', { test: item })}>
                        <Surface style={styles.sectionWrapper} elevation={2}>
                            <View style={styles.leftSection}>
                            <Text style={styles.description}
                            >{item.name}</Text>
                            <Text style={styles.subText}
                            >{item.description}</Text>
                            </View>
                            <View style={styles.rightSection}>
                            <Text>${item.price}</Text>
                           
                            <AntDesign name="right" size={16} color="green" />
                            </View>
                        </Surface>
                        </TouchableOpacity>

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
        margin: 10,
        ...Platform.select({
            web: {
                width: 500,
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
       
        fontWeight: 'bold',
        ...Platform.select({
            web: {
                fontSize: 15,
            },
            default: {
                fontSize: 24,
            }
        })
    },
    description: {
        fontWeight: 'bold',
        ...Platform.select({
            web: {
                fontSize: 15,
            },
            default: {
                fontSize: 18,
            }
        })
    },
    subText:
    {
        color: 'gray',
        ...Platform.select({
            web: {
                fontSize: 15,
            },
            default: {
                fontSize: 16,
            }
        })
    },
    sectionWrapper: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#f0f0f0',
        height: 80,
        marginBottom: 10,
        borderRadius: 5,
        padding: 10,
        margin: 5,
        
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
        backgroundColor: '#fff',
        ...Platform.select({
            web: {
                width: 400,
            },
            default: {
                width: '100%',
            }
        })
    },
};

export default TestListScreen;
