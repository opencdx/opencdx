import React, { useEffect, useState } from 'react';
import { Button, View, StyleSheet, Platform } from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const ListScreen = ({ navigation }) => {
    const [buttonTitles, setButtonTitles] = useState([]);

    useEffect(() => {
        const fetchQuestionnaireList = async () => {
            try {
                const jwtToken = await AsyncStorage.getItem('jwtToken');

                const response = await axios.post(
                    'https://localhost:8080/questionnaire/questionnaire/list',
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
                <Button
                    key={index}
                    title={questionnaire.title}
                    onPress={() => navigation.navigate('Home', { questionnaire })}
                />
            ))}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 8,
        shadowColor: "#000",
        margin: 'auto',
        ...(Platform.select({
            web: {
                minWidth: 500,
            },
            default: {},
        })),
    },
    input: {
        borderColor: 'gray',
        borderWidth: 1,
        padding: 10,
        borderRadius: 5,
        margin: 5,
    },
});

export default ListScreen;
