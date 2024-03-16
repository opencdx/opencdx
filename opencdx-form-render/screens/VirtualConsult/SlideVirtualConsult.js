import React from 'react';
import { View, Text, StyleSheet, Platform, SafeAreaView } from 'react-native';
import { Button, ButtonText, ArrowRightIcon, ArrowLeftIcon } from '@gluestack-ui/themed';
import { Slider } from '@react-native-community/slider';

const SlideVirtualConsult = ({ navigation }) => {
    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.body}>
                <Text style={styles.input}>
                    The following clinical questionnaire is based on CDC guidelines and will help us determine the best course of action with testing.
                </Text>
                <View style={styles.center}>
                    <ArrowLeftIcon />
                    <Slider
                        style={{ width: 200, height: 40 }}
                        minimumValue={1}
                        maximumValue={100}
                        maximumTrackTintColor="#000000"
                    />
                    <ArrowRightIcon />
                </View>
            </View>
            <View style={styles.footer}>
                <View style={styles.center}>
                    <Text style={styles.signup}>Slide to pick</Text>
                </View>
                <Button
                    title="Sign In"
                    style={styles.button}
                    mode='contained-tonal'
                    onPress={() => navigation.navigate('CheckVirtualConsult')}
                >
                    <ButtonText style={styles.buttonText}>Continue</ButtonText>
                </Button>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: '#000',
        backgroundColor: '#fff',
        ...Platform.select({
            web: {
                maxWidth: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            },
        }),
    },
    center: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        paddingTop: 24,
    },
    input: {
        marginBottom: 24,
        textAlign: 'center',
        color: 'gray',
    },
    button: {
        marginBottom: 10,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 68,
        backgroundColor: '#0066FF',
        ...Platform.select({
            web: {
                width: '90%',
            },
            default: {
                width: '90%',
            },
        }),
    },
    buttonText: {
        color: 'white',
    },
    body: {
        ...Platform.select({
            web: {
                padding: 24,
            },
            default: {
                flex: 1,
                justifyContent: 'center',
                padding: 24,
            },
        }),
    },
    footer: {
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 4,
        paddingTop: 24,
    },
});

export default SlideVirtualConsult;
