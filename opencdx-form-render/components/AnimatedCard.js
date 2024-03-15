import React, { useRef } from 'react';
import { View, StyleSheet,Platform } from 'react-native';
import { Button, Text, Surface } from 'react-native-paper';
import { TouchableOpacity } from 'react-native-gesture-handler';
import LottieView from 'lottie-react-native';

const AnimatedCard = ({ navigation, link, src, title, subtitle }) => {
    const animation = useRef(null);

    const restartAnimation = () => {
        animation.current?.reset();
        animation.current?.play();
    };

    return (
        <TouchableOpacity onPress={() => navigation.navigate(link)}>
            <Surface style={styles.surface} elevation={2}>
                <LottieView
                    autoPlay
                    loop={true}
                    ref={animation}
                    style={styles.animation}
                    source={src}
                />
                {title &&<Text variant='titleMedium'>{title}</Text>}
                {subtitle && <Text variant='titleSmall'>{subtitle}</Text>}

                <View style={styles.buttonContainer}>
                    <Button title="Restart Animation" onPress={restartAnimation} />
                </View>
            </Surface>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    surface: {
        margin: 5,
        elevation: 2,
        alignItems: 'center',
        padding: 10,
        
         ...Platform.select({
             web: {
                 marginTop:50,
                 width: 350,
                 height: 350,

             },
             default: {
                    
                   
                
             }
         })

       
       
    },
    animation: {
        ...Platform.select({
            web: {
                width: 250,
                height: 250
               
            },
            default: {
                width: 100,
                height: 100,
            }
        })
    },
    buttonContainer: {
        paddingTop: 20,
    },
});

export default AnimatedCard;
