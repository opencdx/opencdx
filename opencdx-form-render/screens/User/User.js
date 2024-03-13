import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { View, StyleSheet, Platform } from 'react-native';
import { Text } from 'react-native-paper';
import { TouchableOpacity } from 'react-native-gesture-handler';

const User = ({navigation}) => {
    const [buttonTitles, setButtonTitles] = useState([]);
    useEffect(() => {
        const fetchQuestionnaireList = async () => {
            try {
                const jwtToken = await AsyncStorage.getItem('jwtToken');
                const response = await axios.post(
                    '/questionnaire/questionnaire/list',
                    {
                        pagination: {
                            pageSize: 30,
                            sortAscending: true,
                        },
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${jwtToken}`,
                        },
                    }
                );
                const { questionnaires } = response.data;
                setButtonTitles(questionnaires);
            } catch (error) {
                console.error(error);
            }
        };

        fetchQuestionnaireList();
    }, []);
    return (
        <View style={styles.container}>
            {buttonTitles.map((questionnaire, index) => (

                 <TouchableOpacity 
                    onPress={() => navigation.navigate('ListQuestion', { questionnaire: questionnaire })}>
                        <View style={styles.sectionWrapper}>
                        <Text variant='titleMedium' key={index}>{questionnaire.title}</Text>
                        </View>
                        </TouchableOpacity>
            ))}

        </View>
    );
};
const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        shadowOffset: {
            width: 0,
            height: 2,
        },
        marginTop: 100,
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

    sectionWrapper: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#f0f0f0',
        backgroundColor: 'paleturquoise',
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
});



export default User;
