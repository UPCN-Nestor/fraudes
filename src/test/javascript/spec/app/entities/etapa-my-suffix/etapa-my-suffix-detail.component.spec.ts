/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { EtapaMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/etapa-my-suffix/etapa-my-suffix-detail.component';
import { EtapaMySuffixService } from '../../../../../../main/webapp/app/entities/etapa-my-suffix/etapa-my-suffix.service';
import { EtapaMySuffix } from '../../../../../../main/webapp/app/entities/etapa-my-suffix/etapa-my-suffix.model';

describe('Component Tests', () => {

    describe('EtapaMySuffix Management Detail Component', () => {
        let comp: EtapaMySuffixDetailComponent;
        let fixture: ComponentFixture<EtapaMySuffixDetailComponent>;
        let service: EtapaMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [EtapaMySuffixDetailComponent],
                providers: [
                    EtapaMySuffixService
                ]
            })
            .overrideTemplate(EtapaMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtapaMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtapaMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EtapaMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.etapa).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
